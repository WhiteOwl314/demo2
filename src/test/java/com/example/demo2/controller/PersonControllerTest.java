package com.example.demo2.controller;

import com.example.demo2.domain.Person;
import com.example.demo2.domain.dto.PersonDto;
import com.example.demo2.dto.Birthday;
import com.example.demo2.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Slf4j
@Transactional
class PersonControllerTest {


    @Autowired
    PersonController personController;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(personController)
                .setMessageConverters(messageConverter)
                .alwaysDo(print())
                .build();
    }

    @Test
    void getPerson() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("martin"))
                .andExpect(jsonPath("$.hobby").isEmpty())
                .andExpect(jsonPath("$.address").isEmpty())
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.age").isNumber())
                .andExpect(jsonPath("$.birthday").value("1991-08-15"));
    }

    @Test
    void postPerson() throws Exception {

        PersonDto dto = PersonDto.of("martin","programming","대전",LocalDate.now(),"programmer","010-3925-1533");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person?name=martin2&age=20")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(toJasonString(dto)))
                .andExpect(status().isCreated());

        Person result = personRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).get(0);

        assertAll(
                () -> assertThat(result.getName()).isEqualTo("martin"),
                () -> assertThat(result.getHobby()).isEqualTo("programming"),
                () -> assertThat(result.getAddress()).isEqualTo("대전"),
                () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                () -> assertThat(result.getJob()).isEqualTo("programmer"),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("010-3925-1533")
        );
    }

    @Test
    void postPersonIfNameIsNull() throws Exception{

        PersonDto dto = new PersonDto();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJasonString(dto)))
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("알 수 없는 서버 오류가 발생했습니다."));
    }

    @Test
    void modifyPerson() throws Exception{

        PersonDto dto = PersonDto.of("martin","programming","판교",LocalDate.now(),"programmer","010-3925-1533");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJasonString(dto)))
                .andExpect(status().isOk());

        Person result = personRepository.findById(1L).get();

        assertAll(
                () -> assertThat(result.getName()).isEqualTo("martin"),
                () -> assertThat(result.getAddress()).isEqualTo("판교"),
                () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                () -> assertThat(result.getHobby()).isEqualTo("programming"),
                () -> assertThat(result.getJob()).isEqualTo("programmer")
        );
    }

    @Test
    void modifyPersonIfNameIsDifferent() throws Exception{
        PersonDto dto = PersonDto.of("Seongju","programming","대전",LocalDate.now(),"programmer","010-3925-1533");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJasonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("이름 변경을 허용하지 않습니다."));

    }

    @Test
    void modifyPersonIfPersonNotFound() throws Exception{
        PersonDto dto = PersonDto.of("martin","programming","대전",LocalDate.now(),"programmer","010-3925-1533");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/10")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJasonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("\"Person Entity가 존재하지 않습니다.\""));
    }

    @Test
    void modifyName() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                .param("name","martinModified"))
                .andExpect(status().isOk());

        assertThat(personRepository.findById(1L).get().getName()).isEqualTo("martinModified");
    }

    @Test
    void deletePerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andExpect(status().isOk());

        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));


    }

    private String toJasonString(PersonDto personDto) throws JsonProcessingException{

        return objectMapper.writeValueAsString(personDto);
    }
}