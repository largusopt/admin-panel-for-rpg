package com.example.demo.controller;

import com.example.demo.DTO.*;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.model.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/rest/players")
public interface PlayerController {
    @PostMapping
    CreatePlayerResponse createPlayer(@RequestBody @Valid CreatePlayerRequest createPlayerRequest);

    @GetMapping("/{id}")
    ResponseEntity<CreatePlayerResponse> getPlayer(@PathVariable("id") @Positive Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePlayer(@PathVariable("id") @Positive Long id);

    @GetMapping("/count")
    Integer getPlayersCount(/*@RequestParam*/ FilterDTO filterDTO);

    @PostMapping("/{id}")
    CreatePlayerResponse updatePlayer (@PathVariable("id") @Positive Long id, @RequestBody UpdateDTO updateDTO);

    @GetMapping()
    List<CreatePlayerResponse> getPlayersList (FilterDTO filterDTO);
}
