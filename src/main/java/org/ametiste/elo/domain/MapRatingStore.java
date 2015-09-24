package org.ametiste.elo.domain;

import org.ametiste.elo.domain.model.MatchPair;
import org.ametiste.elo.domain.model.Player;
import org.ametiste.elo.domain.model.PlayerInfo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by atlantis on 9/24/15.
 */
public class MapRatingStore implements RatingStore {

    private Map<Integer, Player> playerMap;
    private RankingCalculator calculator;

    public MapRatingStore(RankingCalculator calculator) {
        this.calculator = calculator;
        this.playerMap = new HashMap<>();
    }

    @Override
    public void updateRating(int winnerId, int loserId) {

        if(!playerMap.containsKey(winnerId) || !playerMap.containsKey(loserId)) {
            throw new IllegalArgumentException("No player with winner or loser id is found among the names");
        }
        Player winner = playerMap.get(winnerId);
        Player loser = playerMap.get(loserId);

        winner.rateWinTo(loser, calculator);
    }

    @Override
    public void init(List<PlayerInfo> players) {
        for(PlayerInfo playerInfo: players) {
            playerMap.put(playerInfo.getId(), new Player(playerInfo));
        }
    }

    @Override
    public List<Player> loadSorted(Comparator<Player> sorting) {

        return playerMap.values().stream().sorted(sorting).collect(Collectors.toList());
    }

    @Override
    public Player getPlayerById(int id) {
        if(!playerMap.containsKey(id)) {
            throw new IllegalArgumentException("No player with such id");
        }
        return playerMap.get(id);
    }

    @Override
    public List<MatchPair> getFutureMatches(int matchesNumber) {

        Map<Integer, PlayerInfo> rankToInfo = this.rankToInfo();
        List<MatchPair> pairs = new ArrayList<>();
        for(int i=0; i<matchesNumber; i++) {
            pairs.add(calculator.rankingRandom(rankToInfo));
        }
        return pairs;
    }

    private Map<Integer, PlayerInfo> rankToInfo() {
        Map<Integer, PlayerInfo> rankToInfo  = new HashMap<>();
        for(Player player: playerMap.values()) {
            rankToInfo.put(player.getRanking(), player.getInfo());
        }
        return rankToInfo;
    }


}
