package com.example.demo.repository;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.PlayerMapper;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.model.Player;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

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
    public List<Player> getSortedPlayers(FilterDTO filterDTO, PlayerOrder order) {

         Stream<Player> stream = getPlayers().stream()
                .filter(player -> filterDTO.getName() == null || player.getName().contains(filterDTO.getName()))
                .filter(player -> filterDTO.getTitle() == null || player.getTitle().contains(filterDTO.getTitle()))
                .filter(player -> filterDTO.getRace() == null || player.getRace().equals(filterDTO.getRace()))
                .filter(player -> filterDTO.getProfession() == null || player.getProfession().equals(filterDTO.getProfession()))
                .filter(player -> filterDTO.getBanned() == null || player.getBanned().equals(filterDTO.getBanned()))
                .filter(player -> filterDTO.getAfter() == null || player.getBirthday().after(new Date(filterDTO.getAfter())))
                .filter(player -> filterDTO.getBefore() == null || player.getBirthday().before(new Date(filterDTO.getBefore())))
                .filter(player -> filterDTO.getMinExperience() == null || player.getExperience() >= filterDTO.getMinExperience())
                .filter(player -> filterDTO.getMaxExperience() == null || player.getExperience() <= filterDTO.getMaxExperience())
                .filter(player -> filterDTO.getMinLevel() == null || player.getLevel() >= filterDTO.getMinLevel())
                .filter(player -> filterDTO.getMaxLevel() == null || player.getLevel() <= filterDTO.getMaxLevel());


         if (order != null){
             stream = stream.sorted(getComparator(order));
         }
         return stream.toList();

    }

    private Comparator<Player> getComparator(PlayerOrder order){
        Comparator<Player> comparator = switch (order) {
            case NAME -> Comparator.comparing(Player::getName);
            case EXPERIENCE -> Comparator.comparing(Player::getExperience);
            case BIRTHDAY -> Comparator.comparing(Player::getBirthday);
            case LEVEL -> Comparator.comparing(Player::getLevel);
            default -> Comparator.comparing(Player::getId);
        };
        return  comparator;
    }

    @Override
    public Integer getPlayersCount(FilterDTO filterDTO){
        return getSortedPlayers(filterDTO, null).size();
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
