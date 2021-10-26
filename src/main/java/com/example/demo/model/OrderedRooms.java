package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orderedrooms")
public class OrderedRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "room_id")
    private Long roomId;
    @Column(name = "since")
    private String since;
    @Column(name = "until")
    private String until;
}
