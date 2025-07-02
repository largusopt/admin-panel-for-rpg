package com.example.demo.repository;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.DTO.UpdateDTO;
import com.example.demo.model.SqlBuilderResult;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public boolean deletePlayer(Long id){
        String sqlDelete = "DELETE FROM player WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sqlDelete, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Player> getSortedPlayers(FilterDTO filterDTO, PlayerOrder order) {
         SqlBuilderResult sqlBuilderResult = buildFilteredQuery(filterDTO, order);
         String sql = sqlBuilderResult.getSql();
         List<Object> clausesParams = sqlBuilderResult.getClausesParams();
         return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Player.class), clausesParams.toArray());
    }

    @Override
    public Integer getPlayersCount(FilterDTO filterDTO, PlayerOrder order){
        SqlBuilderResult sqlBuilderResult = buildFilteredQuery(filterDTO, order);
        String sql = sqlBuilderResult.getSql().replaceFirst("SELECT \\*", "SELECT COUNT(*)");
        List<Object> clausesParams = sqlBuilderResult.getClausesParams();
        return jdbcTemplate.queryForObject(sql, Integer.class, clausesParams.toArray());
    }

    @Override
    @Nullable
    public Player getPlayerById(long id){
        String sql = "SELECT * FROM player WHERE id  = ?";
        return  jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Player.class), id);
    }

    @Override
    public Player updatePlayer (Long id, UpdateDTO updateDTO){
        String sql = "UPDATE player SET ";
        List<Object> clausesParams = new ArrayList<>();
        List<String> clauses = new ArrayList<>();
        if ( updateDTO.getName() != null){
            clauses.add("name = ?");
            clausesParams.add(updateDTO.getName());
        }
        if ( updateDTO.getTitle() != null){
            clauses.add("title = ?");
            clausesParams.add(updateDTO.getTitle());
        }
        if ( updateDTO.getExperience() != null){
            clauses.add("experience = ?");
            clausesParams.add(updateDTO.getExperience());
        }
        if ( updateDTO.getProfession() != null){
            clauses.add("profession = ?");
            clausesParams.add(updateDTO.getProfession().toString());
        }
        if ( updateDTO.getRace() != null){
            clauses.add("race = ?");
            clausesParams.add(updateDTO.getRace().toString());
        }
        if ( updateDTO.getBanned() != null){
            clauses.add("banned = ?");
            clausesParams.add(updateDTO.getBanned());
        }
        clausesParams.add(id);
        sql = sql + String.join(", ", clauses) + " WHERE id = ?";
        jdbcTemplate.update(sql, clausesParams.toArray());
        return getPlayerById(id);
    }

    private SqlBuilderResult buildFilteredQuery(FilterDTO filterDTO, PlayerOrder order) {
        String sql = "SELECT * FROM player";
        List<Object> clausesParams = new ArrayList<>();
        List<String> clauses = new ArrayList<>();

        if (filterDTO.getName() != null) {
            clauses.add("name LIKE ?");
            clausesParams.add("%" + filterDTO.getName() + "%");
        }
        if (filterDTO.getTitle() != null) {
            clauses.add("title LIKE ?");
            clausesParams.add("%" + filterDTO.getTitle() + "%");
        }
        if (filterDTO.getRace() != null) {
            clauses.add("race = ?");
            clausesParams.add(filterDTO.getRace().toString());
        }
        if (filterDTO.getProfession() != null) {
            clauses.add("profession = ?");
            clausesParams.add(filterDTO.getProfession().toString());
        }
        if (filterDTO.getAfter() != null) {
            clauses.add("birthday >= ?");
            clausesParams.add(new java.sql.Date(filterDTO.getAfter()));
        }
        if (filterDTO.getBefore() != null) {
            clauses.add("birthday <= ?");
            clausesParams.add(new java.sql.Date(filterDTO.getBefore()));
        }
        if (filterDTO.getBanned() != null) {
            clauses.add("banned = ?");
            clausesParams.add(filterDTO.getBanned());
        }
        if (filterDTO.getMinExperience() != null) {
            clauses.add("experience >= ?");
            clausesParams.add(filterDTO.getMinExperience());
        }
        if (filterDTO.getMaxExperience() != null) {
            clauses.add("experience <= ?");
            clausesParams.add(filterDTO.getMaxExperience());
        }
        if (filterDTO.getMinLevel() != null) {
            clauses.add("level >= ?");
            clausesParams.add(filterDTO.getMinLevel());
        }
        if (filterDTO.getMaxLevel() != null) {
            clauses.add("level <= ?");
            clausesParams.add(filterDTO.getMaxLevel());
        }
         if (order != null){
             sql = sql + "ORDER BY" + getOrderColumn(order);
         }
        if (!clauses.isEmpty()) {
            sql = sql + "WHERE" + String.join("AND", clauses);
        }
        return new SqlBuilderResult(sql,clausesParams);
    }

    private String getOrderColumn(PlayerOrder order) {
        return switch (order) {
            case NAME -> "name";
            case EXPERIENCE -> "experience";
            case BIRTHDAY -> "birthday";
            case LEVEL -> "level";
            default -> "id";
        };
    }
}
