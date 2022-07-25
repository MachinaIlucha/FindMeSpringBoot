package com.findme.findme.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "date_posted")
    private Date datePosted;

    @Column(name = "location_tagged")
    private String location;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_tagged",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_tagged_id"))
    @ToString.Exclude
    private List<User> usersTagged;

    @OneToOne
    @JoinColumn(name = "user_posted_id")
    private User userPosted;

    @OneToOne
    @JoinColumn(name = "user_page_posted_id")
    private User userPagePosted;

    @PrePersist
    public void prePersist() {
        this.datePosted = new Date();
    }
}
