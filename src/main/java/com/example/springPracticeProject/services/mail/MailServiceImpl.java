package com.example.springPracticeProject.services.mail;

import com.example.springPracticeProject.models.Mail;
import com.example.springPracticeProject.repositories.mail.MailJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//
import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailJpaRepository mailRepository;

    @Override
    public void create(Mail mail) {
        mailRepository.save(mail);
    }

    @Override
    public List<Mail> readAll() {
        return mailRepository.findAll();
    }

    @Override
    public Mail read(Long id) {
        return mailRepository.getById(id);
    }

    @Override
    public boolean update(Mail mail, Long id) {
        if (mailRepository.existsById(id)) {
            mail.setId(id);
            mailRepository.save(mail);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (mailRepository.existsById(id)) {
            mailRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
