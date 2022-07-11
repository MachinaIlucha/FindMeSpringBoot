package com.findme.findme.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "age")
    private Integer age;

    @Column(name = "date_registered")
    private Date dateRegistered;

    @Column(name = "date_last_active")
    private Date dateLastActive;

    @Column(name = "relationship_status")
    private String relationshipStatus;

    @Column(name = "religion")
    private String religion;

    @Column(name = "school")
    private String school;

    @Column(name = "university")
    private String university;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @PrePersist
    public void prePersist() {
        if(this.dateRegistered == null)
            dateRegistered = new Date();
        if(this.dateLastActive == null)
            dateLastActive = new Date();
    }
}
