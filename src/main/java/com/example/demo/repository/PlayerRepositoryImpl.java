package com.example.demo.repository;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.PlayerMapper;
import com.example.demo.model.Player;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {
    private long id = 0;
    private List<Player> players = new ArrayList<>();
    private PlayerMapper playerMapper;

    @Override
    public Player addPlayer(Player player){
        generateId(player);
        players.add(player);
        return player;
    }

    private void generateId(Player player){
        player.setId(id);
        id++;
    }

    @Override
    public List<Player> getPlayers(){
        return players;
    }

    @Override
    public void deletePlayer(Player player){
        players.removeIf(p -> p.getId().equals(player.getId()));
    }

    @Override
    public List<Player> getSortedPlayers(FilterDTO filterDTO, String partOfName, String partOfTitle) {
        return getPlayers().stream()
                .filter(player -> filterDTO.getName() == null || player.getName().contains(partOfName))
                .filter(player -> filterDTO.getTitle() == null || player.getTitle().contains(partOfTitle))
                .filter(player -> filterDTO.getRace() == null || player.getRace().equals(filterDTO.getRace()))
                .filter(player -> filterDTO.getProfession() == null || player.getProfession().equals(filterDTO.getProfession()))
                .filter(player -> filterDTO.getBanned() == null || player.getBanned().equals(filterDTO.getBanned()))
                .filter(player -> filterDTO.getAfter() == null || player.getBirthday().after(new Date(filterDTO.getAfter())))
                .filter(player -> filterDTO.getBefore() == null || player.getBirthday().before(new Date(filterDTO.getBefore())))
                .filter(player -> filterDTO.getMinExperience() == null || player.getExperience() >= filterDTO.getMinExperience())
                .filter(player -> filterDTO.getMaxExperience() == null || player.getExperience() <= filterDTO.getMaxExperience())
                .filter(player -> filterDTO.getMinLevel() == null || player.getLevel() >= filterDTO.getMinLevel())
                .filter(player -> filterDTO.getMaxLevel() == null || player.getLevel() <= filterDTO.getMaxLevel())
                .toList();
    }

    @Override
    @Nullable
    public Player getPlayerById(long id){

        return players.stream()
                .filter(player -> player.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
