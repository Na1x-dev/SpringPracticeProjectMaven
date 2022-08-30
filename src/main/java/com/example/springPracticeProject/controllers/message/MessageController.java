package com.example.springPracticeProject.controllers.message;

import com.example.springPracticeProject.models.Mail;
import com.example.springPracticeProject.models.Message;
import com.example.springPracticeProject.services.mail.MailService;
import com.example.springPracticeProject.services.message.MessageService;

import com.example.springPracticeProject.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    public List<Message> reverseList(List<Message> someList) {
        for (int i = 0; i < someList.size(); i++) {
            Message someMessage = someList.get(someList.size() - 1);
            someList.remove(someList.get(someList.size() - 1));
            someList.add(i, someMessage);
        }
        return someList;
    }

    private void addAttributes(Model model, Principal user) {
        final List<Message> receivedMessages = messageService.readReceivedMessages(userService.getUserMailId(user.getName()));
        final List<Message> sendMessages = messageService.readSendMessages(userService.getUserMailId(user.getName()));
        reverseList(sendMessages);
        reverseList(receivedMessages);
        model.addAttribute("receivedMessages", receivedMessages);
        model.addAttribute("sendMessages", sendMessages);
        model.addAttribute("messagePageLink", "../messagePage/index/id=$message.id.toString()");
    }

    @GetMapping(value = MessageEndpoints.receivedMessagesPage)
    public String receivedMessages(Model model, Principal user) {
        addAttributes(model, user);
        return "receivedMessages/index";
    }

    @GetMapping(value = MessageEndpoints.sendMessagesPage)
    public String sendMessages(Model model, Principal user) {
        addAttributes(model, user);
        return "sendMessages/index";
    }

    @GetMapping(value = MessageEndpoints.draftMessagesPage)
    public String draftMessages(Model model, Principal user) {
        addAttributes(model, user);
        return "draftMessages/index";
    }

    @GetMapping(value = MessageEndpoints.favoriteMessagesPage)
    public String favoriteMessages(Model model, Principal user) {
        addAttributes(model, user);
        return "favoriteMessages/index";
    }

    @GetMapping(value = MessageEndpoints.messagePageEnd) //наверное нельзя делать update в методе get
    public String messagePage(Model model, Principal user, @PathVariable(name = "id") Long id) {
        addAttributes(model, user);
        Message currentMessage = messageService.read(id);
        model.addAttribute("currentMessage", currentMessage);
        model.addAttribute("showReplyButton", user.getName().equals(currentMessage.getSendersMail().getUser().getUsername()));
        model.addAttribute("sameThemeMessages", reverseList(messageService.readMessagesOnTheSameTheme(id)));
        if (!currentMessage.isReadStatus() && user.getName().equals(currentMessage.getRecipientsMail().getUser().getUsername()))
            messageService.update(currentMessage, currentMessage.getId());
        return "messagePage/index";
    }

//    @PostMapping("/newMessagePage/index")
//    public String newMessagePage(@ModelAttribute("newMessage") Message message, @ModelAttribute("recipientsMail") Mail recipientsMail, Principal user) {
//        recipientsMail = mailService.getMailByMailAddress(recipientsMail.getMailAddress());
//        Mail sendersMail = userService.getUserMail(user.getName());
//        if(message.getMessageContent().isEmpty() || message.getMessageContent().matches("^ *$")){
//            message.setMessageContent("void message");
//        }
//        if(message.getMessageTheme().isEmpty() || message.getMessageTheme().matches("^ *$")){
//            message.setMessageTheme("void message theme");
//        }
//
//        messageService.create(message, sendersMail, recipientsMail);
//        return "redirect:/receivedMessages/index";
//    }

    @GetMapping("/newMessagePage/index/id={response_message_id}")
    public String newMessagePageReply(Model model, Principal user, @PathVariable("response_message_id") Long previousMessageId) {
        addAttributes(model, user);
        Message message = new Message();
        Mail recipientsMail = new Mail();

        if (previousMessageId != 0) {
            Message previousMessage = messageService.read(previousMessageId);
            model.addAttribute("responseMessageMailAddress", previousMessage.getSendersMail().getMailAddress());
            model.addAttribute("responseMessageTheme", previousMessage.getMessageTheme());
        }
        model.addAttribute("previousMessageId", previousMessageId);
        model.addAttribute("newMessage", message);
        model.addAttribute("recipientsMail", recipientsMail);
//        System.out.println(messageService.readMessagesOnTheSameTheme(message.getId()));

        return "newMessagePage/index";
    }

    @PostMapping("/newMessagePage/index/id={response_message_id}")
    public String newMessagePageReply(@ModelAttribute("newMessage") Message message, @ModelAttribute("recipientsMail") Mail recipientsMail, Principal user, @PathVariable("response_message_id") Long previousMessageId) {
        if (previousMessageId != 0) {
            Message previousMessage = messageService.read(previousMessageId);
            message.setResponseMessage(previousMessage);
            message.setMessageTheme(previousMessage.getMessageTheme());
            recipientsMail.setMailAddress(previousMessage.getSendersMail().getMailAddress());
//            System.out.println(recipientsMail.getMailAddress());
        }
        recipientsMail = mailService.getMailByMailAddress(recipientsMail.getMailAddress());

        Mail sendersMail = userService.getUserMail(user.getName());
        if (message.getMessageContent().isEmpty() || message.getMessageContent().matches("^ *$")) {
            message.setMessageContent("void message");
        }
        if (message.getMessageTheme().isEmpty() || message.getMessageTheme().matches("^ *$")) {
            message.setMessageTheme("void message theme");
        }

        messageService.create(message, sendersMail, recipientsMail);

        return "redirect:/receivedMessages/index";
    }

}
