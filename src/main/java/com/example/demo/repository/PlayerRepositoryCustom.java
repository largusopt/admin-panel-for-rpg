package com.example.demo.repository;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.model.Player;

import java.util.List;

interface PlayerRepositoryCustom {
    List<Player> getSortedPlayers(FilterDTO filterDTO, PlayerOrder order);
    Integer getPlayersCount(FilterDTO filterDTO);
}
