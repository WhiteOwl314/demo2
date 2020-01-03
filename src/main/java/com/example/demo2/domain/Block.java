package com.example.demo2.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class Block {

    @Id
    @GeneratedValue
    private Long Id;

    private String name;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
}
