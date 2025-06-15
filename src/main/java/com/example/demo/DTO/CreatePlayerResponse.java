package com.example.demo.DTO;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.*;

import javax.validation.constraints.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlayerResponse {

    private long id;

    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 1, max = 12, message = "Размер поля должен быть от 1 до 12 символов")
    private String name;

    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 1, max = 30, message = "Размер поля должен быть от 1 до 30 символов")
    private String title;

    private Race race;

    @NotNull (message = "Поле не должно быть пустым")
    private Profession profession;

    @Min(value = 0, message = "Минимальное значение - 0")
    @Max(value = 10000000, message = "Максимальное значение - 0")
    @NotNull (message = "Поле не должно быть пустым")
    @Positive(message = "Значение должно быть положительным.")
    private Integer experience;

    @Positive(message = "Значение должно быть положительным.")
    @NotNull (message = "Поле не должно быть пустым")
    @Min(value = 946684800000L, message = "Дата должна быть не ранее 01.01.2000")
    @Max(value = 32503679904000000L, message = "Дата должна быть не позднее 31.12.3000" )
    private Long birthday;
    private Boolean banned = false;
    private Integer level;
    private Integer untilNextLevel;
}
