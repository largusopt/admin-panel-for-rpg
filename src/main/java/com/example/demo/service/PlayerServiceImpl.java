package com.example.demo.service;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.DTO.PlayerDto;
import com.example.demo.DTO.UpdateDTO;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.model.Player;
import com.example.demo.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    public PlayerDto addPlayer(PlayerDto playerDto){
        Player player = playerMapper.toPlayerFromPlayerDTO(playerDto); //
        setLevel(player);
        setUntilNextLevel(player);
        PlayerDto newPlayerDto = playerMapper.toDTOPlayerFromPlayer(playerRepository.addPlayer(player));
        return newPlayerDto;
    }

    @Override
    @Nullable
    public PlayerDto getPlayerById(Long id){
        Player player = playerRepository.getPlayerById(id); // нашла нужного игрока
        if (player == null) {
            return null;
        }
        return playerMapper.toDTOPlayerFromPlayer(player);
    }

    @Override
    public Integer getPlayersCount(FilterDTO filterDTO){
       return playerRepository.getPlayersCount(filterDTO, null);
    }

    @Override
    public void deletePlayer(Long id) {
        playerRepository.deletePlayer(id);
    }

    @Override
    @Nullable
    public PlayerDto updatePlayer (Long id, UpdateDTO updateDTO) {
        Player player = playerRepository.updatePlayer(id, updateDTO); // нашла нужного игрока
        return  playerMapper.toDTOPlayerFromPlayer(player);
    }

    @Override
    public List<PlayerDto> getPlayersList(FilterDTO filterDTO) {

        List<Player> playersList = playerRepository.getSortedPlayers(filterDTO, filterDTO.getOrder());
          return playersList.stream()
                .skip((long) filterDTO.getPageNumber() * filterDTO.getPageSize())
                .limit(filterDTO.getPageSize())
                .map(player -> playerMapper.toDTOPlayerFromPlayer(player))
                  .toList();
    }

    private void setLevel(Player player){
        int level = (int) Math.floor(Math.sqrt(2500 + 200 * player.getExperience()))/100;
        player.setLevel(level);
    }

    private void setUntilNextLevel(Player player){
        int N = 50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience();
        player.setUntilNextLevel(N);
    }
}
