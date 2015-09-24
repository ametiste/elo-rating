package org.ametiste.elo.interfaces.dto;

import org.ametiste.elo.domain.model.Player;
import org.ametiste.elo.domain.model.PlayerInfo;

import java.util.List;

/**
 * Created by atlantis on 9/24/15.
 */
public class PlayerFullInfo {

    private final PlayerInfo info;
    private final int totalGames;
    private final int score;
    private final int ranking;
    private final int wins;
    private final int loses;
    private final List<PlayerInfo> wonGames;
    private final  List<PlayerInfo> lostGames;

    public PlayerFullInfo(Player player) {
        info = player.getInfo();
        score = player.getScore();
        ranking = player.getRanking();
        totalGames = player.getTotalGames();
        wins = player.getWins();
        loses = player.getLoses();
        wonGames = player.wonGames();
        lostGames = player.lostGames();
    }

    public PlayerInfo getInfo() {
        return info;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public int getScore() {
        return score;
    }

    public int getRanking() {
        return ranking;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public List<PlayerInfo> getWonGames() {
        return wonGames;
    }

    public List<PlayerInfo> getLostGames() {
        return lostGames;
    }
}
