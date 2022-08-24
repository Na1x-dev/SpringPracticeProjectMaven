package com.example.springPracticeProject.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "firstname")
    @NonNull String firstname;

    @Column(name = "lastname")
    @NonNull String lastname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    @NonNull Date birthDate;

    @Column(name = "username")
    @NonNull String username;

    @Column(name = "password")
    @NonNull String password;

    //    @JsonManagedReference
    @OneToOne(mappedBy = "user")
    @NonNull Mail mail;
    @ManyToMany
    @NonNull Set<Role> roles;

    @Transient
    @NonNull String passwordConfirm;

    Integer getAge(){
        Date currentDate = new Date();
        long liveTime = currentDate.getTime()- birthDate.getTime();
        double age = (double) liveTime/ 31556952000L;
        return (int) age;
    }
}

