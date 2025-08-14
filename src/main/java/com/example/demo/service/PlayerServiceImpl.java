package com.example.demo.service;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.DTO.PlayerDto;
import com.example.demo.DTO.UpdateDTO;
import com.example.demo.model.Player;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.PlayerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        Player player = playerMapper.toPlayerFromPlayerDTO(playerDto);
        setLevel(player);
        setUntilNextLevel(player);
        PlayerDto newPlayerDto = playerMapper.toDTOPlayerFromPlayer(playerRepository.save(player));
        return newPlayerDto;
    }

    @Override
    @Nullable
    public PlayerDto getPlayerById(Long id){
        Player player = playerRepository.findById(id).orElse(null); // нашла нужного игрока
        if (player == null) {
            return null;
        }
        return playerMapper.toDTOPlayerFromPlayer(player);
    }

    @Override
    public long getPlayersCount(FilterDTO filterDTO){
       return playerRepository.count(new PlayerSpecification(filterDTO));
    }

    @Override
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    @Nullable
    public PlayerDto updatePlayer (Long id, UpdateDTO updateDTO) {
        Player playerForUpdate = playerRepository.findById(id).orElse(null);

        if (updateDTO.getName() != null) {
            playerForUpdate.setName(updateDTO.getName());
        }
        if (updateDTO.getTitle() != null) {
            playerForUpdate.setTitle(updateDTO.getTitle());
        }
        if (updateDTO.getBirthday() != null) {
            playerForUpdate.setBirthday(new Date(updateDTO.getBirthday()));
        }
        if (updateDTO.getRace() != null) {
            playerForUpdate.setRace(updateDTO.getRace());
        }
        if (updateDTO.getExperience() != null) {
            playerForUpdate.setExperience(updateDTO.getExperience());
            setLevel(playerForUpdate);
            setUntilNextLevel(playerForUpdate);
        }
        if (updateDTO.getProfession() != null) {
            playerForUpdate.setProfession(updateDTO.getProfession());
        }
        if (updateDTO.getBanned() != null) {
            playerForUpdate.setBanned(updateDTO.getBanned());
        }
        Player updatedPlayer = playerRepository.save(playerForUpdate);
        return  playerMapper.toDTOPlayerFromPlayer(updatedPlayer);
    }

    @Override
    public List<PlayerDto> getPlayersList(FilterDTO filterDTO) {

        List<Player> playersList = playerRepository
                .findAll(new PlayerSpecification(filterDTO), PageRequest.of(filterDTO.getPageNumber(), filterDTO.getPageSize(), Sort.by(filterDTO.getOrder().getFieldName())))
                .getContent();

        return playersList.stream()
            .map(player -> playerMapper.toDTOPlayerFromPlayer(player))
              .toList();
    }

    private void setLevel(Player player){
        int level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        player.setLevel(level);
    }

    private void setUntilNextLevel(Player player){
        int N = 50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience();
        player.setUntilNextLevel(N);
    }
}
