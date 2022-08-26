package com.example.springPracticeProject.services.message;
//
import com.example.springPracticeProject.models.Mail;
import com.example.springPracticeProject.models.Message;

import java.util.List;

public interface MessageService {

    void create(Message message, Mail sendersMail, Mail recipientsMail);

    List<Object> readSendMessages(Long mailId);

    List<Object> readReceivedMessages(Long mailId);
    Message read(Long id);

    boolean update(Message message, Long id);

    boolean delete(Long id);

    List<Message> readMessagesOnTheSameTheme(Long id);


}
