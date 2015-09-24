package org.ametiste.elo.domain.model;

/**
 * Created by atlantis on 9/24/15.
 */
public class RankingPair {

    private final int winnerRanking;
    private final int loserRanking;

    public RankingPair(int newWinnerRanking, int newLoserRanking) {
        this.winnerRanking = newWinnerRanking;
        this.loserRanking = newLoserRanking;
    }

    public int winnerRanking() {
        return winnerRanking;
    }

    public int loserRanking() {
        return loserRanking;
    }
}
