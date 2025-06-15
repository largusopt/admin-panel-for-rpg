package com.example.demo.model;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class Player {
    @Positive
    private Long id;

    @NotBlank
    @Size(min = 1, max = 12)
    private String name;

    @NotBlank
    @Size(min = 1, max = 30)
    private String title;

    private Race race;

    private Profession profession;

    @Min(0)
    @Max(10000000)
    private Integer experience;

    private Integer level;
    private Integer untilNextLevel;

    private Date birthday;
    private Boolean banned = false;
}
