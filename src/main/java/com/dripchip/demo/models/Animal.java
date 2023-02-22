package com.dripchip.demo.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Pavel
 * @version 1.0
 * @date 12.02.2023 14:00
 */
@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private List<Long> animalTypes;
    private Long weight;
    private String length;
    private String height;
    private String gender;
    private String lifeStatus;
    private LocalDateTime chippingDateTime;
    private Integer chipperId;
    private Long chippingLocationId;
    private List<Long> visitedLocations;
    private LocalDateTime deathDateTime;

}
