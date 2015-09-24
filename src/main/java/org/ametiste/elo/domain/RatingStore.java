package org.ametiste.elo.domain;

import org.ametiste.elo.domain.model.MatchPair;
import org.ametiste.elo.domain.model.Player;
import org.ametiste.elo.domain.model.PlayerInfo;

import java.util.Comparator;
import java.util.List;


/**
 * Created by atlantis on 9/24/15.
 */
public interface RatingStore {

    void updateRating(int winnerId, int loserId);
    void init(List<PlayerInfo> players);


    Player getPlayerById(int id);
    List<Player> loadSorted(Comparator<Player> sorting);
    List<MatchPair> getFutureMatches(int matchesNumber);
}
