package com.example.demo2.repository;

import com.example.demo2.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void crud(){

        Person person1 = new Person("martin",10);


        person1.setAddress("Test");

        personRepository.save(person1);

        System.out.println(personRepository.findAll());

        List<Person> people = personRepository.findAll();

        assertThat(people.get(0).getAddress()).isEqualTo("Test");
    }

    @Test
    void hashCodeAndEquals(){
        Person person1 = new Person("martin",10);
        Person person2 = new Person("martin", 10);

        System.out.println(person1.equals(person2));
    }
}