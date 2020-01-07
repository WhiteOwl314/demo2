package com.example.demo2.repository;

import com.example.demo2.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByName(String name);

    List<Person> findByBlockIsNull();

    List<Person> findByBirthdayBetween(LocalDate startDate, LocalDate endDate);
}
