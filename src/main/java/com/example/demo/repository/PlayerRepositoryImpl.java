package com.example.demo.repository;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.service.PlayerMapper;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepository {
   private final JdbcTemplate jdbcTemplate;

    @Override
    public Player addPlayer(Player player){
        String sql = "INSERT INTO player(name, title, race, profession, birthday, banned, experience, level, until_next_level) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        Long generatedId = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                player.getName(),
                player.getTitle(),
                player.getRace().toString(),
                player.getProfession().toString(),
                player.getBirthday(),
                player.getBanned(),
                player.getExperience(),
                player.getLevel(),
                player.getUntilNextLevel()
        );
        player.setId(generatedId);
        return player;
    }

    @Override
    public List<Player> getPlayers(){
        String sql = "SELECT * FROM player";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Player.class));
    }

    @Override
    public void deletePlayer(Long id){
        String sqlFind = "SELECT * FROM player WHERE id = ?";
        String sqlDelete = "DELETE FROM player WHERE id = ?";

        // Проверка, существует ли игрок
        List<Player> players = jdbcTemplate.query(sqlFind, new BeanPropertyRowMapper<>(Player.class), id);

        if (!players.isEmpty()) {
            jdbcTemplate.update(sqlDelete, id);
        } else {
            throw new IllegalArgumentException("Player with id " + id + " not found");
        }
    }

    @Override
    public List<Player> getSortedPlayers(FilterDTO filterDTO, PlayerOrder order) {
        String sql = "SELECT * FROM player WHERE name";

         Stream<Player> stream = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Player.class)).stream()
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

    @Override
    public Integer getPlayersCount(FilterDTO filterDTO){
        return getSortedPlayers(filterDTO, null).size();
    }


    @Override
    @Nullable
    public Player getPlayerById(long id){
        String sql = "SELECT * FROM player WHERE id  = ?";

        return  jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Player.class), id);
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
}
