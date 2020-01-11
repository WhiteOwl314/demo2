package com.example.demo2.service;

import com.example.demo2.domain.Block;
import com.example.demo2.domain.Person;
import com.example.demo2.domain.dto.PersonDto;
import com.example.demo2.dto.Birthday;
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
        return personRepository.findByBlockIsNull();
    }

    public List<Person> getPeopleByName(String name){
        return personRepository.findByName(name);
    }

    @Transactional
    public Person getPerson(Long id){
        Person person = personRepository.findById(id).orElse(null);

        log.info("person: {}",person );

        return person;
    }

    @Transactional
    public void put(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto) {

        Person personAtDb = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        if(!personAtDb.getName().equals(personDto.getName())){
            throw new RuntimeException("이름이 다릅니다.");
        }
        personAtDb.setName(personDto.getName());

        if(personDto.getBirthday() != null){
            personAtDb.setBirthday((new Birthday(personDto.getBirthday())));
        }
        personAtDb.setAddress(personDto.getAddress());
        personAtDb.setAge(personDto.getAge());
        personAtDb.setHobby(personDto.getHobby());
        personAtDb.setJob(personDto.getJob());
        personAtDb.setPhoneNumber(personDto.getPhoneNumber());

        personRepository.save(personAtDb);

    }
}
