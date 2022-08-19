package com.example.springPracticeProject.controllers.mail;

import com.example.springPracticeProject.models.Mail;
import com.example.springPracticeProject.models.User;
import com.example.springPracticeProject.services.mail.MailService;
import com.example.springPracticeProject.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MailValidator implements Validator {
    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Mail mail = (Mail) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

        if (mailService.getMailByMailAddress(mail.getMailAddress()) != null) {
            errors.rejectValue("mailAddress", "Duplicate.userMail.mailAddress");
        }
    }
}