package com.example.demo.repository;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long>, PlayerRepositoryCustom{
    Player savePlayer(Player player);
   // List<Player> getPlayers();
    //void deletePlayer(Player player);
    //Player getPlayerById(long id);

    List<Player> getSortedPlayers(FilterDTO filterDTO, PlayerOrder order);
    Integer getPlayersCount(FilterDTO filterDTO);

}
