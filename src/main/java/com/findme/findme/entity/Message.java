package com.findme.findme.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
@Getter
@Setter
@ToString
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Column(name = "text")
    private String text;
    @Column(name = "date_sent")
    private Date dateSent;
    @Column(name = "date_read")
    private Date dateRead;

    @OneToOne
    @JoinColumn(name = "user_from_id")
    private User userFrom;

    @OneToOne
    @JoinColumn(name = "user_to_id")
    private User userTo;
}
