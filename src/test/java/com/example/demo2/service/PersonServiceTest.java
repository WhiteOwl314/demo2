package com.example.demo2.service;

import com.example.demo2.domain.Person;
import com.example.demo2.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @Test
    void getPerson(){
        Person person = personService.getPerson((long)3);
        assertThat(person.getName()).isEqualTo("dennis");
    }

    @Test
    void getPeopleByName(){
        List<Person> result = personService.getPeopleByName("martin");

        result.forEach(System.out::println);
    }
}