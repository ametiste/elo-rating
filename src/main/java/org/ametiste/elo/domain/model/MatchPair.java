package org.ametiste.elo.domain.model;

/**
 * Created by atlantis on 9/24/15.
 */
public class MatchPair {

    private final PlayerInfo player;
    private final PlayerInfo opponent;

    public MatchPair(PlayerInfo player, PlayerInfo opponent) {
        this.player = player;
        this.opponent = opponent;
    }

    public PlayerInfo getPlayer() {
        return player;
    }

    public PlayerInfo getOpponent() {
        return opponent;
    }
}
