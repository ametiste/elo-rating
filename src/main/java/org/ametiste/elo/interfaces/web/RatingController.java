package org.ametiste.elo.interfaces.web;

import org.ametiste.elo.application.EloRatingApp;
import org.ametiste.elo.domain.model.MatchPair;
import org.ametiste.elo.domain.model.Player;
import org.ametiste.elo.interfaces.dto.PlayerFullInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by atlantis on 9/24/15.
 */
@RestController
public class RatingController {

    @Autowired
    private EloRatingApp app;

    @RequestMapping(value = "/ranks")
    public List<Player> ranks() {
        return app.sortPlayersByRanking();
    }

    @RequestMapping(value = "/wins")
    public List<Player> wins() {
        return app.sortPlayersByWins();
    }

    @RequestMapping(value = "/loses")
    public List<Player> loses() {
        return app.sortPlayersByLose();
    }

    @RequestMapping(value = "/scores")
    public List<Player> scores() {
        return app.sortPlayersByScore();
    }

    @RequestMapping(value = "/info/{playerId}")
    public PlayerFullInfo info(@PathVariable("playerId") int playerId) {
        return new PlayerFullInfo(app.getPlayerInfo(playerId));
    }


    @RequestMapping(value = "/next/{number}")
    public List<MatchPair> nextMatches(@PathVariable("number") int number) {
        return app.generateMatches(number);
    }

}
