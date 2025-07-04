package com.example.demo.repository;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>{

    // List<Player> getSortedPlayers(FilterDTO filterDTO, PlayerOrder order);
   // Integer getPlayersCount(FilterDTO filterDTO);

}
