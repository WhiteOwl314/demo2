package com.example.demo2.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;
    @NonNull
    private int age;

    private String hobby;
    private String address;
    private String birthday;
    private String job;

    @ToString.Exclude
    private String phoneNumber;
}
