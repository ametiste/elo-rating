package org.ametiste.elo.domain.model;

import org.ametiste.elo.domain.RankingCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atlantis on 9/24/15.
 */
public class Player {

    private final PlayerInfo info;
    private int score;
    private int ranking;
    private int totalGames;
    private int wins;
    private final static int scoreDelta = 400; // took from a head
    private List<PlayerInfo> wonTo;
    private List<PlayerInfo> lostTo;

    public Player(PlayerInfo info) {

        if(info ==null) {
            throw new IllegalArgumentException("Player info cant be null");
        }

        wonTo = new ArrayList<>();
        lostTo = new ArrayList<>();
        this.info = info;
        this.ranking=2000; // found a mention in wiki for USCF beginners
        this.totalGames = 0;
        this.wins = 0;
        this.score = 0;
    }


    public void rateWinTo(Player loser, RankingCalculator calculator) {
        this.wins ++;
        this.totalGames ++;
        loser.totalGames++;
        RankingPair pair = calculator.calculate(this.ranking, loser.ranking);
        this.ranking = pair.winnerRanking()>100? pair.winnerRanking() : 100;
        loser.ranking = pair.loserRanking()>100? pair.loserRanking() : 100;
        this.score = score + scoreDelta;
        loser.score = score - scoreDelta;
        wonTo.add(loser.info);
        loser.lostTo.add(this.info);
    }

    public int getRanking() {
        return this.ranking;
    }

    public int getScore() {
        return this.score;
    }

    public int getWins() {
        return this.wins;
    }

    public int getLoses() {
        return this.totalGames - this.wins;
    }

    public List<PlayerInfo> wonGames() {
        return  wonTo;
    }

    public List<PlayerInfo> lostGames() {
        return lostTo;
    }

    public PlayerInfo getInfo() {
        return info;
    }

    public int getTotalGames() {
        return totalGames;
    }
}
