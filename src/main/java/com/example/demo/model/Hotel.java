package com.example.demo.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "country")
    private String country;
    @Column(name = "capacity")
    private Long capacity;
}