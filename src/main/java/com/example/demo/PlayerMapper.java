package com.example.demo;

import com.example.demo.DTO.CreatePlayerRequest;
import com.example.demo.DTO.CreatePlayerResponse;
import com.example.demo.DTO.PlayerDto;
import com.example.demo.model.Player;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PlayerMapper {
    public Player toPlayerFromPlayerDTO(PlayerDto playerDto){
        Player player = new Player();
        player.setName(playerDto.getName());
        player.setTitle(playerDto.getTitle());
        player.setRace(playerDto.getRace());
        player.setProfession(playerDto.getProfession());
        player.setBirthday(playerDto.getBirthday());
        player.setBanned(playerDto.getBanned());
        player.setExperience(playerDto.getExperience());
        return player;
    }
    public PlayerDto toDTOPlayerFromCreatePlayerRequest(CreatePlayerRequest createPlayerRequest) {
        PlayerDto newPlayer = new PlayerDto();
        newPlayer.setName(createPlayerRequest.getName());
        newPlayer.setTitle(createPlayerRequest.getTitle());
        newPlayer.setRace(createPlayerRequest.getRace());
        newPlayer.setProfession(createPlayerRequest.getProfession());
        newPlayer.setBirthday(new Date(createPlayerRequest.getBirthday()));
        newPlayer.setBanned(createPlayerRequest.getBanned());
        newPlayer.setExperience(createPlayerRequest.getExperience());
        return newPlayer;
    }

    public CreatePlayerResponse toCreatePlayerResponseFromDTOPlayer(PlayerDto playerDto) {
        CreatePlayerResponse newPlayer = new CreatePlayerResponse();
        newPlayer.setId(playerDto.getId());
        newPlayer.setName(playerDto.getName());
        newPlayer.setTitle(playerDto.getTitle());
        newPlayer.setRace(playerDto.getRace());
        newPlayer.setProfession(playerDto.getProfession());
        newPlayer.setBirthday(playerDto.getBirthday().getTime());
        newPlayer.setBanned(playerDto.getBanned());
        newPlayer.setExperience(playerDto.getExperience());
        newPlayer.setLevel(playerDto.getLevel());
        newPlayer.setUntilNextLevel(playerDto.getUntilNextLevel());
        return newPlayer;
    }

    public PlayerDto toDTOPlayerFromPlayer(Player player) {
        PlayerDto newPlayerDto = new PlayerDto();
        newPlayerDto.setId(player.getId());
        newPlayerDto.setName(player.getName());
        newPlayerDto.setTitle(player.getTitle());
        newPlayerDto.setRace(player.getRace());
        newPlayerDto.setProfession(player.getProfession());
        newPlayerDto.setBirthday(player.getBirthday());
        newPlayerDto.setBanned(player.getBanned());
        newPlayerDto.setExperience(player.getExperience());
        newPlayerDto.setLevel(player.getLevel());
        newPlayerDto.setUntilNextLevel(player.getUntilNextLevel());
        return newPlayerDto;
    }
}
