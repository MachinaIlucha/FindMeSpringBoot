package com.findme.findme.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
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

    @Column(name = "avatar")
    private String avatar;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false, updatable = false)})
    private Set<Role> roles;

    @PrePersist
    public void prePersist() {
        dateRegistered = new Date();
        dateLastActive = new Date();
    }
}
