package org.ametiste.elo.domain.model;

import org.ametiste.elo.domain.RankingCalculator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by atlantis on 9/25/15.
 */
public class PlayerTest {

    private Player player;
    private Player player2;
    @Mock
    private RankingCalculator calculator;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        player = new Player(new PlayerInfo(1, "Name"));
        player2 = new Player(new PlayerInfo(2, "Name2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNullInfo() {
        new Player(null);
    }



    @Test
    public void testGetRankingInitial() throws Exception {
        assertEquals(2000, player.getRanking());
    }

    @Test
    public void testGetScoreInitial() throws Exception {
        assertEquals(0, player.getScore());
    }

    @Test
    public void testGetWinsInitial() throws Exception {
        assertEquals(0, player.getWins());
    }

    @Test
    public void testGetLosesInitial() throws Exception {
        assertEquals(0, player.getLoses());
    }

    @Test
    public void testWonGamesInitial() throws Exception {
        assertEquals(0, player.wonGames().size());
    }

    @Test
    public void testLostGamesInitial() throws Exception {
        assertEquals(0, player.lostGames().size());
    }

    @Test
    public void testGetTotalGamesInitial() throws Exception {
        assertEquals(0, player.getTotalGames());
    }

    @Test
    public void testGetInfoInitial() throws Exception {
        assertEquals(1, player.getInfo().getId());
        assertEquals("Name", player.getInfo().getName());
    }



    @Test
    public void testRateWinTo() throws Exception {
        RankingPair pair = new RankingPair(1000, 800);
        when(calculator.calculate(anyInt(), anyInt())).thenReturn(pair);
        player.rateWinTo(player2, calculator);
        assertEquals(1, player.getTotalGames());
        assertEquals(1, player.getWins());
        assertEquals(0, player.getLoses());
        assertEquals(400, player.getScore());
        assertEquals(1000, player.getRanking());
        assertArrayEquals(Arrays.asList(player2.getInfo()).toArray(), player.wonGames().toArray());
        assertEquals(0, player.lostGames().size());
        assertEquals(1, player2.getTotalGames());
        assertEquals(0, player2.getWins());
        assertEquals(1, player2.getLoses());
        assertEquals(0, player2.getScore());
        assertEquals(800, player2.getRanking());
        assertArrayEquals(Arrays.asList(player.getInfo()).toArray(), player2.lostGames().toArray());
        assertEquals(0, player2.wonGames().size());

    }
}