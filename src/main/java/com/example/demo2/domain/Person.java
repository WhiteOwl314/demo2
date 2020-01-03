package com.example.demo2.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String hobby;
    private String address;
    private String birthday;
    private String job;
    private String phoneNumber;
}
