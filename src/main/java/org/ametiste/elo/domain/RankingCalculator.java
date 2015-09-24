package org.ametiste.elo.domain;

import org.ametiste.elo.domain.model.MatchPair;
import org.ametiste.elo.domain.model.PlayerInfo;
import org.ametiste.elo.domain.model.RankingPair;

import java.util.Map;

/**
 * Created by atlantis on 9/24/15.
 */
public interface RankingCalculator {

    RankingPair calculate(int winnerRanking, int loserRanking);
    MatchPair rankingRandom(Map<Integer, PlayerInfo> rankToPlayer);
}
