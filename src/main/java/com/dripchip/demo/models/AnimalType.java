package com.dripchip.demo.models;

import jakarta.persistence.*;

/**
 * @author Pavel
 * @version 1.0
 * @date 12.02.2023 14:00
 */
@Entity
@Table(name = "animal_types")
public class AnimalType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
}
