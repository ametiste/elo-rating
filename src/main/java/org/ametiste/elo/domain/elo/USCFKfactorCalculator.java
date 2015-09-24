package org.ametiste.elo.domain.elo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atlantis on 9/24/15.
 */
public class USCFKfactorCalculator implements KfactorCalculator {
    private List<RankFactor> factors;

    public USCFKfactorCalculator() {
        factors = new ArrayList<>();
        factors.add(new RankFactor(0, 2100, 32));
        factors.add(new RankFactor(2100, 2400, 24));
        factors.add(new RankFactor(2400, Integer.MAX_VALUE, 16));
    }

    @Override
    public int getFactor(int winnerRanking) {

        if(winnerRanking<0) {
            throw new IllegalArgumentException();
        }
        for(RankFactor factor: factors) {
            if(factor.isInRange(winnerRanking)) {
                return factor.getFactor();
            }
        }
        throw new IllegalStateException("Something is totally wrong, rank cant be higher than Integer.MAX_VALUE");
    }

    @Override
    public List<Integer> sameFactor(int memberRank, List<Integer> ranks) {
        RankFactor memberRankFactor = null;
        for(RankFactor factor: factors) {
            if(factor.isInRange(memberRank)) {
                memberRankFactor = factor;
                break;
            }
        }
        if(memberRankFactor==null) {
            throw new IllegalArgumentException("Incorrect member rank");
        }

        List<Integer> sameRank = new ArrayList<>();
        for(Integer rank: ranks) {
            if(memberRankFactor.isInRange(rank)) {
                sameRank.add(rank);
            }
        }
        return sameRank;
    }

    private class RankFactor {

        private final int lowerBound;
        private final int upperBound;
        private final int factor;

        public RankFactor(int lowerBound, int upperBound, int factor) {

            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.factor = factor;
        }

        public boolean isInRange(int number) {
            return number >= lowerBound && number < upperBound;
        }

        public int getFactor() {
            return factor;
        }
    }
}
