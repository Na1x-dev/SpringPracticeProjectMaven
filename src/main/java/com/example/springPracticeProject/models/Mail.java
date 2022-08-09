package com.example.springPracticeProject.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "mails")
@Data
@NoArgsConstructor
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Mail {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")

    @NonNull
    User user;

    @Column(name = "mail_address")
    @NonNull
    String mailAddress;

    @JsonIgnore //попробовать заменить как для user-mail
    @OneToMany(mappedBy = "recipientsMail", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    List<Message> receivedMessages;

    @JsonIgnore
    @OneToMany(mappedBy = "sendersMail", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    List<Message> sentMessages;
}
