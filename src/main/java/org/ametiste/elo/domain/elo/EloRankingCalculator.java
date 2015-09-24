package org.ametiste.elo.domain.elo;

import org.ametiste.elo.domain.RankingCalculator;
import org.ametiste.elo.domain.model.MatchPair;
import org.ametiste.elo.domain.model.PlayerInfo;
import org.ametiste.elo.domain.model.RankingPair;

import java.util.*;

/**
 * Created by atlantis on 9/24/15.
 */
public class EloRankingCalculator implements RankingCalculator{

    private KfactorCalculator kfactor;
    private static final int WIN_SCORE = 1;
    private static final int LOSE_SCORE = 0;

    public EloRankingCalculator(KfactorCalculator kfactor) {

        this.kfactor = kfactor;
    }

    @Override
    public RankingPair calculate(int winnerRanking, int loserRanking) {
        double winnerExpectation = 1/(1+ Math.pow(10, (loserRanking-winnerRanking)/400d));
        int newWinnerRanking = (int)(winnerRanking + kfactor.getFactor(winnerRanking)*(WIN_SCORE-winnerExpectation));
        int newLoserRanking = (int)(loserRanking + kfactor.getFactor(loserRanking)*(LOSE_SCORE-(1-winnerExpectation)));
        return new RankingPair(newWinnerRanking, newLoserRanking);
        //this coupled to one data object for ease of expectation calculations since expectationB = 1- expectationA
    }

    @Override
    public MatchPair rankingRandom(Map<Integer, PlayerInfo> rankToPlayer) {
        if(rankToPlayer.size()<2) {
            throw new IllegalArgumentException("Cant calculate match pair out of " + rankToPlayer.size() + " elements");
        }

        List<Integer> ranks = new ArrayList<>(rankToPlayer.keySet());
        ranks.sort(Comparator.<Integer>naturalOrder());
        int memberId = new Random().nextInt(ranks.size());
        int memberRank = ranks.get(memberId);
        ranks.remove(memberId); // to not participate in random
        List<Integer> sameRank = kfactor.sameFactor(memberRank, ranks);
        int memberOpponentRank;
        if(!sameRank.isEmpty()) {
            int memberOpponentId = new Random().nextInt(sameRank.size());
            memberOpponentRank =  sameRank.get(memberOpponentId);
        }
        else {
            int memberOpponentId = new Random().nextInt(ranks.size());
            memberOpponentRank =  ranks.get(memberOpponentId);

        }
        return new MatchPair(rankToPlayer.get(memberRank), rankToPlayer.get(memberOpponentRank));
    }


}
