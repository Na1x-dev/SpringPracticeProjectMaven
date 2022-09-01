package com.example.springPracticeProject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

@Entity
@Table(name = "messages")
@Getter
@Setter
@RequiredArgsConstructor
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
    @JoinColumn(name = "next_message_id", referencedColumnName = "id")
    Message nextMessage;

    @OneToOne
    @JoinColumn(name="previous_message_id", referencedColumnName = "id")
    Message previousMessage;


//    @Column(name="response_message_id")
//    Long responseMessageId;


    public String returnDateOfMessage() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM");
        Date today = new Date();
        if(messageDate.getYear() != today.getYear()){
            dateFormat = new SimpleDateFormat("d MMMM yyyy");
        }
        String strDate = dateFormat.format(messageDate);

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

    public String returnTimeOfMessage(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(messageDate);
    }

    public String returnStringReadStatus() {
        return readStatus ? "Прочитано" : "Не прочитано";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Message message = (Message) o;
        return id != null && Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", messageContent='" + messageContent + '\'' +
                '}';
    }
}
