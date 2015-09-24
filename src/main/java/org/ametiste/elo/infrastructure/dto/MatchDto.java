package org.ametiste.elo.infrastructure.dto;

/**
 * Created by atlantis on 9/24/15.
 */
public class MatchDto {

    private final int winner;
    private final int loser;

    public MatchDto(int winner, int loser) {

        this.winner = winner;
        this.loser = loser;
    }

    public int winner() {
        return winner;
    }

    public int loser() {
        return loser;
    }
}
