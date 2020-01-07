package com.example.demo2.service;

import com.example.demo2.domain.Block;
import com.example.demo2.domain.Person;
import com.example.demo2.repository.BlockRepository;
import com.example.demo2.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    public List<Person> getPeopelExcludeBlocks(){
        List<Person> people = personRepository.findAll();

        return people.stream().filter(person -> person.getBlock() == null).collect(Collectors.toList());
    }

    public List<Person> getPeopleByName(String name){
        List<Person> people = personRepository.findAll();

        return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
    }

    @Transactional
    public Person getPerson(Long id){
        Person person = personRepository.findById(id).get();

        System.out.println("Person: " + person);
        log.info("person: {}",person );

        return person;
    }
}
