package com.example.demo.repository;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.DTO.UpdateDTO;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.model.Player;

import java.util.List;

public interface PlayerRepository{
    Player addPlayer(Player player);
    boolean deletePlayer(Long id);
    Player getPlayerById(long id);
    List<Player> getSortedPlayers(FilterDTO filterDTO, PlayerOrder order);
    Integer getPlayersCount(FilterDTO filterDTO, PlayerOrder order);
    Player updatePlayer (Long id, UpdateDTO updateDTO);
}
