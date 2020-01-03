package com.example.demo2.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int age;

    private String hobby;
    private String address;
    private String birthday;
    private String job;
    private String phoneNumber;
}
