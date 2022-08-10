package com.example.springPracticeProject.services.mail;
//
import com.example.springPracticeProject.models.Mail;
import java.util.List;

public interface MailService {

    void create(Mail mail);
    List<Mail> readAll();

    Mail read(Long id);

    boolean update(Mail mail, Long id);

    boolean delete(Long id);

    Mail getMailByMailAddress(String mailAddress);
}
