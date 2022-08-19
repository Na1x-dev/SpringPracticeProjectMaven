package com.example.springPracticeProject.repositories.message;
//
import com.example.springPracticeProject.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageJpaRepository extends JpaRepository<Message, Long> {
    List<Object> findByRecipientsMailId(Long mailId);
    List<Object> findBySendersMailId(Long mailId);

    Message getById(Long id);
}
