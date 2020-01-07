package com.example.demo2.repository;

import com.example.demo2.domain.Person;
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

        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());

        Map<Person, Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());

        System.out.println(map);
        System.out.println(map.get(person2));
    }

    @Test
    void findByBirthdayBetween(){
        givenPerson("martin",10, LocalDate.of(1991,8,15));
        givenPerson("david",9, LocalDate.of(1992, 7, 10));
        givenPerson("dennis",8, LocalDate.of(1993,1,5));
        givenPerson("sophia",7, LocalDate.of(1994,6,30));
        givenPerson("benny",6, LocalDate.of(1995,8,30));

        List<Person> result = personRepository.findByBirthdayBetween(LocalDate.of(1991,8,1),LocalDate.of(1991,8,31));

        result.forEach(System.out::println);
    }

    private void givenPerson(String name, int age, LocalDate birthday) {

        Person person = new Person(name, age);
        person.setBirthday(birthday);

        personRepository.save(person);
    }
}