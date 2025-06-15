package com.example.demo.DTO;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.model.Player;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
public class PlayerDto extends Player {

    private Long id;
    private  String name;
    private  String title;
    private  Race race;
    private  Profession profession;
    private  Integer experience;
    private Integer level;
    private Integer untilNextLevel;
    private Date birthday;
    private  Boolean banned = false;
}
