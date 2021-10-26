package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users_and_orders")
public class UsersAndOrdersTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "hotel_name")
    private String hotelName;
    @Column(name = "room_number")
    private Long room;
    @Column(name = "since")
    private String since;
    @Column(name = "until")
    private String until;
}
