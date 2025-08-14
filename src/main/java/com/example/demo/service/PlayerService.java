package com.example.demo.service;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.DTO.PlayerDto;
import com.example.demo.DTO.UpdateDTO;

import java.util.List;

public interface PlayerService {
     PlayerDto addPlayer(PlayerDto playerDto);  // PlayerDto .... (PlayerDto)
    // List<PlayerDto> getPlayers();
     PlayerDto getPlayerById(Long id);
     void deletePlayer(Long id);
     long getPlayersCount(FilterDTO filterDTO);
     PlayerDto updatePlayer (Long id, UpdateDTO updateDTO);
     List<PlayerDto> getPlayersList(FilterDTO filterDTO);


}
