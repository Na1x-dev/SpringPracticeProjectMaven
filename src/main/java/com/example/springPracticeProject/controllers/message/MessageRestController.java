package com.example.springPracticeProject.controllers.message;

import com.example.springPracticeProject.models.Message;
import com.example.springPracticeProject.services.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageRestController {
    private final MessageService messageService;

    @Autowired
    MessageRestController(MessageService messageService){
        this.messageService = messageService;
    }

    //    @PostMapping(value = MessageEndpoints.messageEndpoint)
//    public ResponseEntity<?> create(@RequestBody Message message){
//        messageService.create(message);
//        return new ResponseEntity<>(message, HttpStatus.CREATED);
//    }
//

    //
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
