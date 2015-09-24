package org.ametiste.elo.application;

import org.ametiste.elo.domain.RatingStore;
import org.ametiste.elo.domain.model.MatchPair;
import org.ametiste.elo.domain.model.Player;
import org.ametiste.elo.domain.model.PlayerInfo;
import org.ametiste.elo.infrastructure.MatchesRepository;
import org.ametiste.elo.infrastructure.dto.MatchDto;
import org.ametiste.elo.infrastructure.dto.PlayerDto;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by atlantis on 9/24/15.
 */
public class EloRatingApp {


    private MatchesRepository repository;
    private RatingStore store;

    public EloRatingApp(MatchesRepository repository, RatingStore store) {

        this.repository = repository;
        this.store = store;
    }

    public void calculate() {

        List<PlayerInfo> infos = repository.loadPlayers().stream().map(this::create).collect(Collectors.toList());
        store.init(infos); //dont want dto to leak to store
        repository.loadMatches().forEach(this::update);
    }

    private void update(MatchDto match) {
        store.updateRating(match.winner(), match.loser());
    }

    private PlayerInfo create(PlayerDto dao) {
        return new PlayerInfo(dao.getId(), dao.getName());
    }

    public List<Player> sortPlayersByRanking() {
        return store.loadSorted((o1, o2) -> o1.getRanking() - o2.getRanking());
    }

    public List<Player> sortPlayersByScore() {
        return store.loadSorted((o1, o2) -> o1.getScore() - o2.getScore());
    }

    public List<Player> sortPlayersByWins() {
        return store.loadSorted((o1, o2) -> o1.getWins() - o2.getWins());
    }

    public List<Player> sortPlayersByLose() {
        return store.loadSorted((o1, o2) -> o1.getLoses() - o2.getLoses());
    }


    public Player getPlayerInfo(int id) {
        return store.getPlayerById(id);
    }


    public List<MatchPair> generateMatches(int matchesNumber) {
        return  store.getFutureMatches(matchesNumber);
    }

}
