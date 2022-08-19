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

    public void reverseList(List<Object> someList) {
        for (int i = 0; i < someList.size(); i++) {
            Object someObject = someList.get(someList.size() - 1);
            someList.remove(someList.get(someList.size() - 1));
            someList.add(i, someObject);
        }
    }

    private void addAttributes(Model model, Principal user) {
        final List<Object> receivedMessages = messageService.readReceivedMessages(userService.getUserMailId(user.getName()));
        final List<Object> sendMessages = messageService.readSendMessages(userService.getUserMailId(user.getName()));
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
        if (!currentMessage.isReadStatus() && user.getName().equals(currentMessage.getRecipientsMail().getUser().getUsername()))
            messageService.update(currentMessage, currentMessage.getId());
        return "messagePage/index";
    }

    @GetMapping("/newMessagePage/index")
    public String newMessagePage(Model model, Principal user) {
        addAttributes(model, user);
        Message message = new Message();
        Mail recipientsMail = new Mail();
        model.addAttribute("newMessage", message);
        model.addAttribute("recipientsMail", recipientsMail);
        return "newMessagePage/index";
    }

    @PostMapping("/newMessagePage/index")
    public String newMessagePage(@ModelAttribute("newMessage") Message message, @ModelAttribute("recipientsMail") Mail recipientsMail, Principal user) {
        recipientsMail = mailService.getMailByMailAddress(recipientsMail.getMailAddress());
        Mail sendersMail = userService.getUserMail(user.getName());
        messageService.create(message, sendersMail, recipientsMail);
        return "redirect:/receivedMessages/index";
    }

}
