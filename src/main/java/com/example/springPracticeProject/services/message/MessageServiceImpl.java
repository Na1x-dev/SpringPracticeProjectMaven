package com.example.springPracticeProject.services.message;

import com.example.springPracticeProject.models.Mail;
import com.example.springPracticeProject.models.Message;
import com.example.springPracticeProject.repositories.message.MessageJpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageJpaRepository messageRepository;

    public MessageServiceImpl(MessageJpaRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void create(Message message, Mail sendersMail, Mail recipientsMail) {
        message.setRecipientsMail(recipientsMail);
        message.setSendersMail(sendersMail);
        message.setReadStatus(false);
        message.setMessageDate(new Date());
//        message.setResponseMessage(null);
        messageRepository.save(message);
    }


    @Override
    public Message read(Long id) {
        return messageRepository.getById(id);
    }

    @Override
    public boolean update(Message message, Long id) {
        if (messageRepository.existsById(id)) {
            message.setId(id);
            message.setReadStatus(true);
            messageRepository.save(message);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Message> readMessagesOnTheSameTheme(Long id) {
        List<Message> messages = new ArrayList<>();
        messages.add(messageRepository.getById(id));
        while (messages.get(messages.size() - 1).getPreviousMessage() != null) {
            messages.add(messages.get(messages.size() - 1).getPreviousMessage());
//            System.out.println(messages.get(messages.size()-1));
        }

        return messages;
    }

    @Override
    public List<Message> readReceivedMessages(Long mailId) {
        List<Message> someMessages = messageRepository.findByRecipientsMailId(mailId);
        if(someMessages==null){
            someMessages = new ArrayList<>();
        }
        return someMessages;
    }

    @Override
    public List<Message> readSendMessages(Long mailId) {
        List<Message> someMessages = messageRepository.findBySendersMailId(mailId);
        if (someMessages == null) {
            someMessages = new ArrayList<>();
        }
        return someMessages;
    }


    @Override
    public List<Message> readAll() {
        return messageRepository.findAll();
    }
}
