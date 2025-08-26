package com.example.demo.controller;

import com.example.demo.DTO.*;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.service.PlayerMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerMapper playerMapper;

    @Test
    void CreatePlayerTest() throws Exception {
        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest();
        createPlayerRequest.setName("Таня");
        createPlayerRequest.setTitle("Укротитель львов");
        createPlayerRequest.setProfession(Profession.DRUID);
        createPlayerRequest.setBirthday(94668480004350L);
        createPlayerRequest.setBanned(false);
        createPlayerRequest.setExperience(500);
        createPlayerRequest.setRace(Race.HUMAN);

        mockMvc.perform(
                post("/rest/players")
                        .content(objectMapper.writeValueAsString(createPlayerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Таня"))
                .andExpect(jsonPath("$.title").value("Укротитель львов"))
                .andExpect(jsonPath("$.profession").value("DRUID"))
                .andExpect(jsonPath("$.birthday").value(94668480004350L))
                .andExpect(jsonPath("$.banned").value(false))
                .andExpect(jsonPath("$.experience").value("500"))
                .andExpect(jsonPath("$.race").value("HUMAN"));
    }

    @Test
    void CreatePlayerWithLongNameTest() throws Exception {
        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest();
        createPlayerRequest.setName("Таняяяяяяяяяяяяяяяяя");
        createPlayerRequest.setTitle("Укротитель львов");
        createPlayerRequest.setProfession(Profession.DRUID);
        createPlayerRequest.setBirthday(94668480004350L);
        createPlayerRequest.setBanned(false);
        createPlayerRequest.setExperience(500);
        createPlayerRequest.setRace(Race.HUMAN);

        mockMvc.perform(
                        post("/rest/players")
                                .content(objectMapper.writeValueAsString(createPlayerRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").value("Размер поля должен быть от 1 до 12 символов"));
    }

    @Test
    void getPlayerByIdTest() throws Exception {
        mockMvc.perform(
                get("/rest/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ниус"));
    }

    @Test
    void deletePlayerTest() throws Exception {
        CreatePlayerRequest player = new CreatePlayerRequest();
        player.setName("Удаляемый");
        player.setTitle("Тестовый");
        player.setRace(Race.HUMAN);
        player.setProfession(Profession.ROGUE);
        player.setBirthday(94668480004350L);
        player.setBanned(false);
        player.setExperience(1000);
        String response = mockMvc.perform(
                post("/rest/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player))
        )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(
                        delete("/rest/players/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        assertFalse(playerRepository.findById(id).isPresent());
    }

    @Test
    void updatePlayerNameTest() throws Exception {
        CreatePlayerRequest player = new CreatePlayerRequest();
        player.setName("ToUpdate");
        player.setTitle("Rogue");
        player.setRace(Race.HUMAN);
        player.setProfession(Profession.ROGUE);
        player.setBirthday(946684800000L);
        player.setExperience(500);
        player.setBanned(false);


        String response = mockMvc.perform(post("/rest/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(response).get("id").asLong();

       String updatedName = "{\"name\":\"Updated\"}";

        mockMvc.perform(
                post("/rest/players/" + id)
                        .content(updatedName)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));

    }

    @Test
    void getPlayersCountTest() throws Exception {
        CreatePlayerRequest player1 = new CreatePlayerRequest();
        player1.setName("Alice");
        player1.setTitle("Hero");
        player1.setRace(Race.HUMAN);
        player1.setProfession(Profession.WARRIOR);
        player1.setBirthday(946684800000L);
        player1.setExperience(1000);
        player1.setBanned(false);


        mockMvc.perform(
                post("/rest/players/")
                        .content(objectMapper.writeValueAsString(player1))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());


        FilterDTO filterDTO1 = new FilterDTO();
        filterDTO1.setName("Ali");
        filterDTO1.setBanned(false);

        mockMvc.perform(
                get("/rest/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", filterDTO1.getName())
                        .param("banned", String.valueOf(filterDTO1.getBanned()))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].banned").value(false));

        mockMvc.perform(get("/rest/players/count")
                        .param("name", filterDTO1.getName())
                        .param("banned", String.valueOf(filterDTO1.getBanned())))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

}