package org.ametiste.elo.domain;

import org.ametiste.elo.domain.model.MatchPair;
import org.ametiste.elo.domain.model.Player;
import org.ametiste.elo.domain.model.PlayerInfo;
import org.ametiste.elo.domain.model.RankingPair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by atlantis on 9/25/15.
 */
public class MapRatingStoreTest {


    private MapRatingStore store;

    @Mock
    private RankingCalculator calculator;

    private List<PlayerInfo> info;

    private PlayerInfo info1 = new PlayerInfo(1, "Name1");
    private PlayerInfo info2 = new PlayerInfo(2, "Name2");
    private PlayerInfo info3 = new PlayerInfo(3, "Name3");

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        store = new MapRatingStore(calculator);
        info = new ArrayList<>();
        info.add(info1);
        info.add(info2);
        info.add(info3);
        store.init(info);

    }


    @Test
    public void testUpdateRating() throws Exception {
        when(calculator.calculate(anyInt(), anyInt())).thenReturn(new RankingPair(1200, 800));
        store.updateRating(1, 2);
        verify(calculator).calculate(2000, 2000);
        store.updateRating(1, 2);
        verify(calculator).calculate(1200, 800);
    }


    @Test
    public void testLoadSorted() throws Exception {
        List<Player>players = store.loadSorted((p1, p2) -> p2.getInfo().getId() - p1.getInfo().getId());
        // just to sort it easier, backwards;
        assertEquals(3, players.size());
        assertEquals(3, players.get(0).getInfo().getId());
        assertEquals(2, players.get(1).getInfo().getId());
    }

    @Test
    public void testGetPlayerById() throws Exception {
        Player player =  store.getPlayerById(1);
        assertEquals(1, player.getInfo().getId());
        assertEquals("Name1", player.getInfo().getName());
    }

    @Test
    public void testGetFutureMatches() throws Exception {
        MatchPair pair1 = new MatchPair(info1, info2);
        MatchPair pair2 = new MatchPair(info2, info3);
        MatchPair pair3 = new MatchPair(info1, info3);
        when(calculator.rankingRandom(anyMap())).thenReturn(pair1).thenReturn(pair2).thenReturn(pair3);
        List<MatchPair> futureMatches = store.getFutureMatches(3);
        assertArrayEquals(Arrays.asList(pair1, pair2, pair3).toArray(), futureMatches.toArray());
    }
}