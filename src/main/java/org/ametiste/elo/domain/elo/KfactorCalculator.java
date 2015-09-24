package org.ametiste.elo.domain.elo;

import java.util.List;

/**
 * Created by atlantis on 9/24/15.
 */
public interface KfactorCalculator {

    int getFactor(int winnerRanking);

    List<Integer> sameFactor(int memberRank, List<Integer> ranks);
}
