package com.example.demo2.repository;

import com.example.demo2.domain.Person;
import com.example.demo2.dto.Birthday;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void crud(){

        Person person1 = new Person("martin",10);

        person1.setBirthday(new Birthday(LocalDate.of(1994,9,14)));

        person1.setAddress("Test");

        personRepository.save(person1);

        System.out.println(personRepository.findAll());

        List<Person> people = personRepository.findAll();

        assertThat(person1.getAddress()).isEqualTo("Test");
    }

    @Test
    void findByBirthdayBetween(){
        List<Person> result = personRepository.findByMonthOfBirthday(8);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("sophia");
    }

}