package com.example.demo2.service;

import com.example.demo2.domain.Block;
import com.example.demo2.domain.Person;
import com.example.demo2.repository.BlockRepository;
import com.example.demo2.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    @Test
    void getPeopleExcludeBlocks(){
        givenPeaple();
        givenBlocks();


        List<Person> result = personService.getPeopelExcludeBlocks();
        System.out.println(result);
    }

    private void givenBlocks() {
        givenBlock("martin");
    }

    private void givenBlock(String name) {
        blockRepository.save(new Block(name));
    }

    private void givenPeaple() {
        givenPerson("martin",10);
        givenPerson("david",9);
        givenPerson("dannis",7);
        givenPerson("martin",11);
    }

    private void givenPerson(String name, int age) {
        personRepository.save(new Person(name,age));
    }

}