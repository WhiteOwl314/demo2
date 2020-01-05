package com.example.demo2.service;

import com.example.demo2.domain.Block;
import com.example.demo2.domain.Person;
import com.example.demo2.repository.BlockRepository;
import com.example.demo2.repository.PersonRepository;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
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

        List<Person> result = personService.getPeopelExcludeBlocks();
        result.forEach(System.out::println);
    }

    //person을 통해 block에 데이터를 set 할 수 있는지 테스트  -> set이 안됨
    @Test
    void cascadeTest(){
        givenPeaple();

        List<Person> result = personRepository.findAll();

        result.forEach(System.out::println);

        Person person = result.get(3);
        person.getBlock().setStartDate(LocalDate.now());
        person.getBlock().setEndDate(LocalDate.now());

        personRepository.save(person);

        personRepository.findAll().forEach(System.out::println);
    }


    private void givenPeaple() {
        givenPerson("martin",10);
        givenPerson("david",9);
        givenPerson("dannis",7);
        givenBlockPerson("martin",11);
    }

    private void givenBlockPerson(String name, int age) {
        Person blockPerson = new Person(name, age);
        blockPerson.setBlock(new Block(name));
        personRepository.save(blockPerson);
    }

    private void givenPerson(String name, int age) {
        personRepository.save(new Person(name,age));
    }

}