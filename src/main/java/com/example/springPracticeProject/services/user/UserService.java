package com.example.springPracticeProject.services.user;

import com.example.springPracticeProject.models.Mail;
import com.example.springPracticeProject.models.User;
//
import java.util.List;

public interface UserService {
    void create(User user);

    User getUser(Long id);
    User findByUsername(String username);

    Long getUserMailId(String username);
}
