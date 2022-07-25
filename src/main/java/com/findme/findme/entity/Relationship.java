package com.findme.findme.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "relationship")
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private Long id;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_from_id")
    private User userFrom;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_to_id")
    private User userTo;

    @Column(name = "relationship_status")
    @Enumerated(EnumType.STRING)
    private RelationshipType status;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "date_last_updated")
    private Date dateLastUpdated;

    @PrePersist
    public void prePersist() {
        this.status = RelationshipType.WAITING;
        this.dateCreated = new Date();
        this.dateLastUpdated = new Date();
    }

}