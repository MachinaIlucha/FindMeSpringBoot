package com.findme.findme.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "messages")
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

    @PrePersist
    public void prePersist() {
        dateSent = new Date();
    }
}
