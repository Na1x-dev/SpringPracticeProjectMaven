package com.example.springPracticeProject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    @Column(name = "message_theme")
    @NonNull
    String messageTheme;

    @Column(name = "message_content")
    @NonNull
    String messageContent;


    @Column(name = "read_status")
    boolean readStatus;

    @Column(name = "message_date")
    @NonNull
    Date messageDate;

    @OneToOne
    @JoinColumn(name = "response_message_id", referencedColumnName = "id")
//    @NonNull
    Message responseMessage;

    public String returnDateOfMessage() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM");
        String strDate = dateFormat.format(messageDate);
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime();
        if (messageDate.getDay() == today.getDay() && messageDate.getMonth() == today.getMonth() && messageDate.getYear() == today.getYear()) {
            strDate = "Сегодня";
        }
        if (messageDate.getDay() == yesterday.getDay() && messageDate.getMonth() == yesterday.getMonth() && messageDate.getYear() == yesterday.getYear()) {
            strDate = "Вчера";
        }
        return strDate;
    }

    public String returnStringReadStatus() {
        return readStatus ? "Прочитано" : "Не прочитано";
    }
}
