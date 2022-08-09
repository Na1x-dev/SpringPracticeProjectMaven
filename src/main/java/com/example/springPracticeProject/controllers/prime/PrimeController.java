package com.example.springPracticeProject.controllers.prime;

import com.example.springPracticeProject.controllers.user.UserEndpoints;
import com.example.springPracticeProject.models.Message;
import com.example.springPracticeProject.models.User;
import com.example.springPracticeProject.services.message.MessageService;

import com.example.springPracticeProject.services.security.SecurityService;
import com.example.springPracticeProject.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class PrimeController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    private void addAttributes(Model model, Principal user){
        final List<Message> receivedMessages = messageService.readReceivedMessages(userService.getUserMailId(user.getName()));
        final List<Message> sendMessages = messageService.readSendMessages(userService.getUserMailId(user.getName()));
        model.addAttribute("receivedMessages", receivedMessages);
        model.addAttribute("sendMessages", sendMessages);
        model.addAttribute("messagePageLink", "../messagePage/index/id=$message.id.toString()");
    }

    @GetMapping(value = PrimeEndpoints.receivedMessagesPage)
    public String receivedMessages(Model model, Principal user){
        addAttributes(model, user);
        return "receivedMessages/index";
    }

    @GetMapping(value = PrimeEndpoints.sendMessagesPage)
    public String sendMessages(Model model, Principal user){
        addAttributes(model, user);
        return "sendMessages/index";
    }
//^([A-z0-9\._-])*
    @GetMapping(value = PrimeEndpoints.draftMessagesPage)
    public String draftMessages(Model model, Principal user){
        addAttributes(model, user);
        return "draftMessages/index";
    }

    @GetMapping(value = PrimeEndpoints.favoriteMessagesPage)
    public String favoriteMessages(Model model, Principal user){
        addAttributes(model, user);
        return "favoriteMessages/index";
    }

    @GetMapping(value = PrimeEndpoints.messagePageEnd)
    public String messagePage(Model model, Principal user, @PathVariable(name = "id") Long id){
        addAttributes(model, user);
        model.addAttribute("currentMessage", messageService.read(id));
        return "messagePage/index";
    }


//    @GetMapping(value = PrimeEndpoints.receivedMessagesPage)
//    public String receivedMessages(Model model){
//        final List<Message> messages = messageService.readReceivedMessages();
//        model.addAttribute("messages", messages);
//        return "receivedMessages/index";
//    }
//
//    @GetMapping(value = PrimeEndpoints.sendMessagesPage)
//    public String sendMessages(Model model){
//        final List<Message> messages = messageService.readSendMessages();
//        model.addAttribute("messages", messages);
//        return "sendMessages/index";
//    }
//
//    @GetMapping(value = PrimeEndpoints.draftMessagesPage)
//    public String draftMessages(Model model){
////        final List<Message> messages = messageService.readAll();
////        model.addAttribute("messages", messages);
//        return "draftMessages/index";
//    }
//
//    @GetMapping(value = PrimeEndpoints.favoriteMessagesPage)
//    public String favoriteMessages(Model model){
////        final List<Message> messages = messageService.readAll();
////        model.addAttribute("messages", messages);
//        return "favoriteMessages/index";
//    }
//
//    @GetMapping(value = PrimeEndpoints.messagePageEnd)
//    public String messagePage(Model model){
////        final List<Message> messages = messageService.readAll();
////        model.addAttribute("messages", messages);
//        return "messagePage/index";
//    }

//    @GetMapping(value = PrimeEndpoints.logInEndpoint)
//    public String logInPage(Model model){
//        return "signUpPage/index";
//    }

//    @GetMapping(value = PrimeEndpoints.defaultEndpoint)
//    public String defaultPage(){
//        return "redirect:signUpPage/index";
//    }

//    @GetMapping(value = MessageEndpoints.messageIdEndpoint)
//    public ResponseEntity<Message> read(@PathVariable(name = "id") Long id){
//        final Message message = messageService.read(id);
//        return message != null
//                ? new ResponseEntity<>(message, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PutMapping(value = MessageEndpoints.messageIdEndpoint)
//    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Message message){
//        final boolean updated = messageService.update(message, id);
//        return updated
//                ? new ResponseEntity<>(message, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }
//
//    @DeleteMapping(value = MessageEndpoints.messageIdEndpoint)
//    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id){
//        final boolean deleted = messageService.delete(id);
//        return deleted
//                ? new ResponseEntity<>(id, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }
//
//    @GetMapping(value = MessageEndpoints.messageMailIdEndpoint)
//    public ResponseEntity<List<Message>> readByMailId(@PathVariable(name = "id") Long id){
//        final List<Message> messages = messageService.readByMailId(id);
//        return messages != null && !messages.isEmpty()
//                ? new ResponseEntity<>(messages, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
