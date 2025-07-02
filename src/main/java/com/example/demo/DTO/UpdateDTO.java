package com.example.demo.DTO;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UpdateDTO {
    @Size(min = 1, max = 12, message = "Имя должно содержать от 1 до 12 символов.")
    private final String name;

    @Size(min = 1, max = 30, message = "Титул должен содержать от 1 до 12 символов.")
    private final String title;

    private final Race race;

    private final Profession profession;

    @Min(0)
    @Max(10000000)
    private final Integer experience;

    @Positive
    @Min(value = 946684800000L, message = "Дата должна быть не ранее 01.01.2000")
    @Max(value = 32503679904000000L, message = "Дата должна быть не позднее 31.12.3000" )
    private final Long birthday;

    private final Boolean banned;
}