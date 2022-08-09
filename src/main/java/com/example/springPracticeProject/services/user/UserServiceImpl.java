package com.example.springPracticeProject.services.user;

import com.example.springPracticeProject.models.Mail;
import com.example.springPracticeProject.models.Role;
import com.example.springPracticeProject.models.User;
import com.example.springPracticeProject.repositories.mail.MailJpaRepository;
import com.example.springPracticeProject.repositories.role.RoleJpaRepository;
import com.example.springPracticeProject.repositories.user.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    private MailJpaRepository mailRepository;
    @Autowired
    private RoleJpaRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Long getUserMailId(String username){
        return mailRepository.findByUserId(userRepository.getIdByUsername(username).getId()).getId();
    }
}
