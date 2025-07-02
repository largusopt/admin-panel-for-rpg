package com.example.demo.service;

import com.example.demo.DTO.CreatePlayerResponse;
import com.example.demo.DTO.FilterDTO;
import com.example.demo.DTO.PlayerDto;
import com.example.demo.DTO.UpdateDTO;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.model.Player;

import java.util.List;

public interface PlayerService {
     PlayerDto addPlayer(PlayerDto playerDto);  // PlayerDto .... (PlayerDto)
    // List<PlayerDto> getPlayers();
     PlayerDto getPlayerById(Long id);
     void deletePlayer(Long id);
     Integer getPlayersCount(FilterDTO filterDTO);
     PlayerDto updatePlayer (Long id, UpdateDTO updateDTO);
     List<PlayerDto> getPlayersList(FilterDTO filterDTO, PlayerOrder playerOrder, Integer pageNumber, Integer pageSize);


}
