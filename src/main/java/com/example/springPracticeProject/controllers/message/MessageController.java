package com.example.springPracticeProject.controllers.message;

import com.example.springPracticeProject.models.Mail;
import com.example.springPracticeProject.models.Message;
import com.example.springPracticeProject.models.User;
import com.example.springPracticeProject.services.mail.MailService;
import com.example.springPracticeProject.services.message.MessageService;

import com.example.springPracticeProject.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MessageController {
    private User infoAboutUser;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

//    public List<Message> reverseList(List<Message> someList) {
//        if (someList == null) {
//            someList = new ArrayList<>();
//        }
//        for (int i = 0; i < someList.size(); i++) {
//            Message someMessage = someList.get(someList.size() - 1);
//            someList.remove(someList.get(someList.size() - 1));
//            someList.add(i, someMessage);
//        }
//        return someList;
//    }

    public List<Message> sortList(List<Message> someList){

//        System.out.println(Math.toIntExact(someList.get(0).getMessageDate().getTime()-someList.get(1).getMessageDate().getTime()));
        Comparator <Message> messageComparator = (o1, o2) -> (Math.toIntExact(o1.getMessageDate().getTime() - o2.getMessageDate().getTime()));
        someList.sort(messageComparator.reversed());


        return someList;
    }

    public List<Message> optimizeList(List<Message> someList) {
        List<Message> optimizedList = new ArrayList<>();
        for (Message message : someList) {
            if (message.getNextMessage() == null) {
                optimizedList.add(message);
            }
        }
        return optimizedList;
    }

    private void addAttributes(Model model, Principal user) {
        if (infoAboutUser == null || !user.getName().equals(infoAboutUser.getUsername()))
            infoAboutUser = userService.findByUsername(user.getName());
        List<Message> receivedMessages = messageService.readReceivedMessages(userService.getUserMailId(user.getName()));
        List<Message> sendMessages = messageService.readSendMessages(userService.getUserMailId(user.getName()));
        receivedMessages = sortList(optimizeList(receivedMessages));
        sendMessages = sortList(optimizeList(sendMessages));
        model.addAttribute("receivedMessages", receivedMessages);
        model.addAttribute("sendMessages", sendMessages);
        model.addAttribute("infoAboutUser", infoAboutUser);
        model.addAttribute("messagePageLink", "../messagePage/index/id=$message.id.toString()");
    }



    @GetMapping(value = "profilePage/index")
    public String profilePage(Model model, Principal user) {
        addAttributes(model, user);
        return "profilePage/index";
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

    @GetMapping(value = MessageEndpoints.trashMessagesPage)
    public String trashMessages(Model model, Principal user) {
        addAttributes(model, user);
        return "trashMessages/index";
    }

    @GetMapping(value = MessageEndpoints.messagePageEnd) //наверное нельзя делать update в методе get
    public String messagePage(Model model, Principal user, @PathVariable(name = "id") Long id) {
        addAttributes(model, user);
        Message currentMessage = messageService.read(id);
        model.addAttribute("currentMessage", currentMessage);
        model.addAttribute("showReplyButton", user.getName().equals(currentMessage.getSendersMail().getUser().getUsername()));
        model.addAttribute("sameThemeMessages", sortList(messageService.readMessagesOnTheSameTheme(id)));
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

    @GetMapping("/newMessagePage/index/id={previous_message_id}")
    public String newMessagePageReply(Model model, Principal user, @PathVariable("previous_message_id") Long previousMessageId) {
        addAttributes(model, user);
        Message currentMessage = new Message();
        Mail recipientsMail = new Mail();
        if (previousMessageId != 0) {
            Message previousMessage = messageService.read(previousMessageId);
            model.addAttribute("currentMessageMailAddress", previousMessage.getSendersMail().getMailAddress());
            model.addAttribute("currentMessageTheme", previousMessage.getMessageTheme()); //для вывода на фронт
        }
        model.addAttribute("previousMessageId", previousMessageId);
        model.addAttribute("currentMessage", currentMessage);
        model.addAttribute("recipientsMail", recipientsMail);
//        System.out.println(messageService.readMessagesOnTheSameTheme(message.getId()));

        return "newMessagePage/index";
    }

    @PostMapping("/newMessagePage/index/id={previous_message_id}")
    public String newMessagePageReply(@ModelAttribute("currentMessage") Message currentMessage, @ModelAttribute("recipientsMail") Mail recipientsMail, Principal user, @PathVariable("previous_message_id") Long previousMessageId) {
        Message previousMessage = null;
        if (previousMessageId != 0) {
            previousMessage = messageService.read(previousMessageId);
            currentMessage.setPreviousMessage(previousMessage);
            currentMessage.setMessageTheme(previousMessage.getMessageTheme()); //для базы данных
            recipientsMail.setMailAddress(previousMessage.getSendersMail().getMailAddress());
            previousMessage.setNextMessage(currentMessage);
        }
        recipientsMail = mailService.getMailByMailAddress(recipientsMail.getMailAddress());

        Mail sendersMail = userService.getUserMail(user.getName());
        if (currentMessage.getMessageContent().isEmpty() || currentMessage.getMessageContent().matches("^ *$")) {
            currentMessage.setMessageContent("void message");
        }
        if (currentMessage.getMessageTheme().isEmpty() || currentMessage.getMessageTheme().matches("^ *$")) {
            currentMessage.setMessageTheme("void message theme");
        }

        messageService.create(currentMessage, sendersMail, recipientsMail);
        if (previousMessage != null)
            messageService.update(previousMessage, previousMessageId);

        return "redirect:/receivedMessages/index";
    }

}
