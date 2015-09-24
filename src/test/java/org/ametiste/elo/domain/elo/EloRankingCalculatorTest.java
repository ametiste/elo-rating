package org.ametiste.elo.domain.elo;

import org.ametiste.elo.domain.model.MatchPair;
import org.ametiste.elo.domain.model.PlayerInfo;
import org.ametiste.elo.domain.model.RankingPair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by atlantis on 9/25/15.
 */
public class EloRankingCalculatorTest {

    private EloRankingCalculator calculator;

    @Mock
    private KfactorCalculator kfactor;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        calculator = new EloRankingCalculator(kfactor);

    }

    @Test
    public void testCalculateEqualRatings() throws Exception {

        when(kfactor.getFactor(2)).thenReturn(10);
        when(kfactor.getFactor(3)).thenReturn(10);
        // E_A = 1  / {1 + 10^{(R_B - R_A)/400}}.
        // E_A + E_B = 1.
        // R_A^\prime = R_A + K(S_A - E_A).
        // for equal ratings E_A= 1/(1+1) = 0.5
        // if ratings equal 2 and 3, and K factor is 10, then,
        // R_A = 2 + 10(1-0.5) = 7 and R_B = 3 + 10(0-0.5) = -2
        RankingPair calculate = calculator.calculate(2, 3);
        assertEquals(7, calculate.winnerRanking());
        assertEquals(-2, calculate.loserRanking());
    }

    @Test
    public void testCalculateDifferentRatings() throws Exception {

        when(kfactor.getFactor(20)).thenReturn(30);
        when(kfactor.getFactor(3)).thenReturn(10);
        // E_A = 1  / {1 + 10^{(R_B - R_A)/400}}.
        // E_A + E_B = 1.
        // R_A^\prime = R_A + K(S_A - E_A).
        // for different ratings E_A= 0.514;
        // R_A = 20 + 30(1-0.514) = 34 and R_B = 3 + 10(0-0.486) = -1
        System.out.println(Math.pow(10, -0.025 ));
        RankingPair calculate = calculator.calculate(20, 3);
        assertEquals(34, calculate.winnerRanking());
        assertEquals(-1, calculate.loserRanking());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRankingRandomWithEmptyMap() throws Exception {

        Map<Integer, PlayerInfo> map = new HashMap<>();


        calculator.rankingRandom(map);
    }

    @Test
    public void testRankingRandom() throws Exception {
        PlayerInfo p1 =  new PlayerInfo(1,"name1");
        PlayerInfo p2 =  new PlayerInfo(2,"name2");
        PlayerInfo p3 =  new PlayerInfo(3,"name3");
        List<Integer> same = Arrays.asList(1800);

        Map<Integer, PlayerInfo> map = new HashMap<>();
        map.put(2300, p1);
        map.put(1900, p2);
        map.put(1800, p3);

        when(kfactor.sameFactor(anyInt(), any(List.class))).thenReturn(same);
        MatchPair matchPair = calculator.rankingRandom(map);
        assertEquals(3, matchPair.getOpponent().getId());
    }
}