package com.harut.resourceservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Table(name = "resource")
@Data
@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_contents")
    private byte[] resource;

    @Column(name = "date_created")
    private LocalTime dateCreated;
}
