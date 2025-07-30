package com.example.demo.controller;

import com.example.demo.DTO.*;
import com.example.demo.service.PlayerMapper;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlayerControllerImpl implements PlayerController {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public CreatePlayerResponse createPlayer(CreatePlayerRequest createPlayerRequest){  // CreatePlayerResponse .... (CreataePlayerRequest)
        PlayerDto playerDto = playerMapper.toDTOPlayerFromCreatePlayerRequest(createPlayerRequest);
        PlayerDto newPlayerDto = playerService.addPlayer(playerDto);
        return playerMapper.toCreatePlayerResponseFromDTOPlayer(newPlayerDto);
    }

    public ResponseEntity<CreatePlayerResponse> getPlayer(Long id){
        PlayerDto playerDto = playerService.getPlayerById(id);
        if (playerDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(playerMapper.toCreatePlayerResponseFromDTOPlayer(playerDto));
    }

    public ResponseEntity<Void> deletePlayer(Long id){
        if ( playerService.getPlayerById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        playerService.deletePlayer(id);
        return ResponseEntity.ok().build();
    }

    public Integer getPlayersCount(/*@RequestParam*/ FilterDTO filterDTO) {
        return playerService.getPlayersCount(filterDTO);
    }

    public CreatePlayerResponse updatePlayer(Long id, UpdateDTO updateDTO){
        PlayerDto playerDto = playerService.updatePlayer(id, updateDTO);
        return playerMapper.toCreatePlayerResponseFromDTOPlayer(playerDto);
    }

    public List<CreatePlayerResponse> getPlayersList(FilterDTO filterDTO, PlayerOrder playerOrder, Integer pageNumber, Integer pageSize){
        return playerService.getPlayersList(filterDTO, playerOrder, pageNumber, pageSize).stream()
                .map(playerDto -> playerMapper.toCreatePlayerResponseFromDTOPlayer(playerDto))
                .collect(Collectors.toList());
    }
}
