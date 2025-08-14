package com.example.demo.model;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Entity
//@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 12)
    private String name;

    @NotBlank
    @Size(min = 1, max = 30)
    private String title;

    @Enumerated(EnumType.STRING)
    private Race race;

    @Enumerated(EnumType.STRING)
    private Profession profession;

    @Min(0)
    @Max(10000000)
    private Integer experience;

    private Integer level;

    @Column(name = "until_next_level")
    private Integer untilNextLevel;

    private Date birthday;
    private Boolean banned = false;
}
