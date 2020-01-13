package com.example.demo2.domain;

import com.example.demo2.domain.dto.PersonDto;
import com.example.demo2.dto.Birthday;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@RequiredArgsConstructor
@Where(clause = "deleted = false")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    @NotEmpty
    private String name;

    private String hobby;
    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String job;

    @ToString.Exclude
    private String phoneNumber;

    @ColumnDefault("0")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Block block;

    public void set(PersonDto personDto){
        if(!StringUtils.isEmpty(personDto.getHobby())){
            this.setHobby(personDto.getHobby());
        }
        if(!StringUtils.isEmpty(personDto.getAddress())){
            this.setAddress(personDto.getAddress());
        }
        if(!StringUtils.isEmpty(personDto.getJob())){
            this.setJob(personDto.getJob());
        }
        if(!StringUtils.isEmpty(personDto.getPhoneNumber())){
            this.setPhoneNumber(personDto.getPhoneNumber());
        }
    }

    public Integer getAge(){

        if(this.birthday != null){

            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday() + 1;
        } else{
            return null;
        }

    }

    public boolean isBirthdayToday(){
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYearOfBirthday(),this.birthday.getMonthOfBirthday(),this.birthday.getDayOfBirthday()));
    }
}
