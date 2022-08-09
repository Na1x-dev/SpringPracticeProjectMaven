package com.example.springPracticeProject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Message {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    Long id;

    @ManyToOne
    @JoinColumn(name = "senders_mail_id", nullable = false, referencedColumnName = "id")
    @NonNull
    Mail sendersMail;

    @ManyToOne
    @JoinColumn(name = "recipients_mail_id", nullable = false, referencedColumnName = "id")
    @NonNull
    Mail recipientsMail;

    @Column(name = "message_content")
    @NonNull
    String messageContent;
}
