package com.example.springPracticeProject.controllers.mail;

import com.example.springPracticeProject.models.Mail;
import com.example.springPracticeProject.services.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MailController {

    private final MailService mailService;

    @Autowired
    MailController(MailService mailService){
        this.mailService = mailService;
    }

//    @PostMapping(value = MailEndpoints.mailEndpoint)
//    public ResponseEntity<?> create(@RequestBody Mail mail){
//        mailService.create(mail);
//        return new ResponseEntity<>(mail, HttpStatus.CREATED);
//    }

    @GetMapping(value = MailEndpoints.mailEndpoint)
    public String list(Model model){
        final List<Mail> mails = mailService.readAll();
        model.addAttribute("mails", mails);
        return "receivedMessages";
    }

    @GetMapping(value = MailEndpoints.mailIdEndpoint)
    public ResponseEntity<Mail> read(@PathVariable(name = "id") Long id){
        final Mail mail = mailService.read(id);
        return mail != null
                ? new ResponseEntity<>(mail, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//
//    @PutMapping(value = MailEndpoints.mailIdEndpoint)
//    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Mail mail){
//        final boolean updated = mailService.update(mail, id);
//        return updated
//                ? new ResponseEntity<>(mail, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }
//
//    @DeleteMapping(value = MailEndpoints.mailIdEndpoint)
//    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id){
//        final boolean deleted = mailService.delete(id);
//        return deleted
//                ? new ResponseEntity<>(id, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }
}