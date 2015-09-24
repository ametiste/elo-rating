package org.ametiste.elo.infrastructure.fs;

import org.ametiste.elo.infrastructure.MatchesRepository;
import org.ametiste.elo.infrastructure.dto.MatchDto;
import org.ametiste.elo.infrastructure.dto.PlayerDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by atlantis on 9/24/15.
 */
public class FileMatchesRepository implements MatchesRepository {

    private final String namesFilePath;
    private final String matchesFilePath;

    public FileMatchesRepository(String namesFilePath, String matchesFilePath) {
        if(namesFilePath==null || namesFilePath.isEmpty()) {
            throw new IllegalArgumentException("Names file path should not be null or empty");
        }

        if(matchesFilePath==null || matchesFilePath.isEmpty()) {
            throw new IllegalArgumentException("Matches file path should not be null or empty");
        }
        this.namesFilePath = namesFilePath;
        this.matchesFilePath = matchesFilePath;
    }


    @Override
    public List<PlayerDto> loadPlayers() {
        return getFileContent(namesFilePath, this::parsePlayer);
    }

    @Override
    public List<MatchDto> loadMatches() {
        return getFileContent(matchesFilePath, this::parseMatch);
    }

    private <T> List<T> getFileContent(String fileName, Function<String, T> function) {
        try {
            return Files.lines(new File(fileName).toPath()).map(String::trim)
                    .filter(s -> !s.isEmpty()).map(function).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Wrong file name or path. Filename: " + fileName);
        }
    }

    private MatchDto parseMatch(String line) {
        String[] split = line.split(" ");
        if(split.length!=2) {
            throw new UnsupportedFileFormatException("File line should consist of two records separated by space");
        }
        return new MatchDto(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    private PlayerDto parsePlayer(String line) {
        String[] split = line.split(" ");
        if(split.length!=2) {
            throw new UnsupportedFileFormatException("File line should consist of two records separated by space");
        }
        return new PlayerDto(Integer.parseInt(split[0]), split[1]);
    }
}
