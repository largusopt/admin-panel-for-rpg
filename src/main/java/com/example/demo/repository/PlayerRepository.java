package com.example.demo.repository;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.model.Player;

import java.util.List;

public interface PlayerRepository {
    Player addPlayer(Player player);

    List<Player> getPlayers();

    void deletePlayer(Player player);

    List<Player> getSortedPlayers(FilterDTO filterDTO, String partOfName, String partOfTitle);

    Player getPlayerById(long id);
}
