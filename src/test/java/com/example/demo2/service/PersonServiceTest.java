package com.example.demo2.service;

import com.example.demo2.domain.Person;
import com.example.demo2.domain.dto.PersonDto;
import com.example.demo2.dto.Birthday;
import com.example.demo2.exception.PersonNotFoundException;
import com.example.demo2.exception.RenameNotPermittedException;
import com.example.demo2.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;

    @Test
    void getAll(){
        when(personRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Lists.newArrayList(new Person("martin"),new Person("denis"), new Person("tony"))));

        Page<Person> result = personService.getAll(PageRequest.of(0,3));

        assertThat(result.getNumberOfElements()).isEqualTo(3);
        assertThat(result.getContent().get(0).getName()).isEqualTo("martin");
        assertThat(result.getContent().get(1).getName()).isEqualTo("denis");
        assertThat(result.getContent().get(2).getName()).isEqualTo("tony");
    }

    @Test
    void getPeopleByName(){
        when(personRepository.findByName("martin")).thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }

    @Test
    void getPerson(){
        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("martin")));

        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("martin");
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        Person person = personService.getPerson(1L);

        assertThat(person).isNull();
    }
    
    @Test
    void put(){
        personService.put(mockPersonDto());

        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeInserted()));
    }

    @Test
    void modifyIfPersonNotFound(){
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.modify(1L,mockPersonDto()));
    }

    @Test
    void modifyIfNameIsDifferent(){
        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("tony")));

        assertThrows(RenameNotPermittedException.class , () -> personService.modify(1L,mockPersonDto()));
    }

    @Test
    void modify(){
        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L,mockPersonDto());

        verify(personRepository, times(1)).save(any(Person.class));
        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeUpdated()));
    }

    @Test
    void modifyByNameIfPersonIsNotFound(){
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.modify(1L,"danial"));
    }

    private PersonDto mockPersonDto() {
        return PersonDto.of("martin","programming","대전", LocalDate.now(),"programmer","010-3925-1533");
    }

    @Test
    void modifyByName(){
        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L,"danial");

        verify(personRepository,times(1)).save(argThat(new IsNameWillBeUpdated()));
    }

    @Test
    void deleteIfPersonNotFound(){
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.delete(1L));
    }

    @Test
    void delete(){
        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("martin")));

        personService.delete(1L);

        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeDeleted()));
    }

    private class IsPersonWillBeInserted implements ArgumentMatcher<Person> {
        @Override
        public boolean matches(Person person) {
            return equals(person.getName(), "martin")
                    && equals(person.getAddress(), "대전")
                    && equals(person.getHobby(), "programming")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(),"programmer")
                    && equals(person.getPhoneNumber(),"010-3925-1533");
        }
        private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
        }

    }

    private class IsPersonWillBeUpdated implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(), "martin")
                    && equals(person.getAddress(), "대전")
                    && equals(person.getHobby(), "programming")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(),"programmer")
                    && equals(person.getPhoneNumber(),"010-3925-1533");
        }
        private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
        }

    }


    private static class IsNameWillBeUpdated implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return person.getName().equals("danial");
        }
    }

    private static class IsPersonWillBeDeleted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.isDeleted();
        }
    }
}