package com.example.demo.DTO;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class FilterDTO {
    private String name;
    private String title;
    private Race race;
    private Profession profession;

    @Min(value = 946684800000L, message = "Дата должна быть не ранее 01.01.2000")
    @Max(value = 32503679904000000L, message = "Дата должна быть не позднее 31.12.3000" )
    private Long after;

    @Min(value = 946684800000L, message = "Дата должна быть не ранее 01.01.2000")
    @Max(value = 32503679904000000L, message = "Дата должна быть не позднее 31.12.3000" )
    private Long before;

    private Boolean banned;
    private Integer minExperience;
    private Integer maxExperience;
    private Integer minLevel;
    private Integer maxLevel;
}
