package com.example.springPracticeProject.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
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

    @Column(name = "age")
    @NonNull Integer age;

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


}

