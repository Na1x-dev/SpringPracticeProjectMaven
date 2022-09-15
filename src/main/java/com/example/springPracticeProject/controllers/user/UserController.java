package com.example.springPracticeProject.controllers.user;

import com.example.springPracticeProject.controllers.mail.MailValidator;
import com.example.springPracticeProject.models.Mail;
import com.example.springPracticeProject.models.Message;
import com.example.springPracticeProject.models.User;
import com.example.springPracticeProject.services.mail.MailService;
import com.example.springPracticeProject.services.message.MessageService;
import com.example.springPracticeProject.services.security.SecurityService;
import com.example.springPracticeProject.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private MailValidator mailValidator;

    @Autowired
    private MessageService messageService;

    @GetMapping("/signUpPage/index")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        User user = new User();
        Mail mail = new Mail();
        model.addAttribute("userForm", user);
        model.addAttribute("userMail", mail);
        return "signUpPage/index";
    }

    @PostMapping("/signUpPage/index")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResultUser, @ModelAttribute("userMail") Mail userMail, BindingResult bindingResultMail) {
        userMail.setMailAddress(userMail.getMailAddress().concat("@ggm.com"));
        userValidator.validate(userForm, bindingResultUser);
        mailValidator.validate(userMail, bindingResultMail);
        if (bindingResultUser.hasErrors() || bindingResultMail.hasErrors()) {
            userMail.setMailAddress("");
//            userMail.setMailAddress(userMail.getMailAddress().substring(0, userMail.getMailAddress().length()-8));//не пашет толком
            return "signUpPage/index";
        }
        userService.create(userForm);
        userMail.setUser(userForm);
        //userMail.setMailAddress(userForm.getUsername().toLowerCase() + "@ggm.com");
        mailService.create(userMail);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/receivedMessages/index";
    }

    @GetMapping("/logInPage/index")
    public String login(Model model, String error, String logout) {
        List<Message> messageList = messageService.readAll();
        initializeDeleteVariable(messageList);
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "logInPage/index";
    }

    @GetMapping("/logout")
    public String logout(Model model, String error, String logout) {
        return "logInPage/index";
    }
    @GetMapping({"/"})
    public String startPage(Model model) {
        return "redirect:receivedMessages/index";
    }


    public void initializeDeleteVariable(List<Message> messageList){
        for(Message message : messageList){
            if(message.getDeletedFromSender() == null){
                message.setDeletedFromSender(0);
            }
            if(message.getDeletedFromRecipient() == null){
                message.setDeletedFromRecipient(0);
            }
            messageService.update(message, message.getId());
        }
    }


}