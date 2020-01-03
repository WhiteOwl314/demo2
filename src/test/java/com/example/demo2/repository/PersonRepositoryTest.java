package com.example.demo2.repository;

import com.example.demo2.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void crud(){

        Person person1 = new Person();

        person1.setAddress("Test");

        personRepository.save(person1);

        System.out.println(personRepository.findAll());
    }
}