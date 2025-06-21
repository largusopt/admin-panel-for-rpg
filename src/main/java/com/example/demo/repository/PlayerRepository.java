package com.example.demo.repository;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.model.Player;

import java.util.Comparator;
import java.util.List;

public interface PlayerRepository {
    Player addPlayer(Player player);

    List<Player> getPlayers();

    void deletePlayer(Player player);

    List<Player> getSortedPlayers(FilterDTO filterDTO, PlayerOrder order);
    Integer getPlayersCount(FilterDTO filterDTO);

    Player getPlayerById(long id);

}
