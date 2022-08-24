package com.example.springPracticeProject.repositories.user;
//
import com.example.springPracticeProject.models.Mail;
import com.example.springPracticeProject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findIdByUsername(String username);
    Date findBirthDateByUsername(String username);
    User findUserById(Long id);
}
