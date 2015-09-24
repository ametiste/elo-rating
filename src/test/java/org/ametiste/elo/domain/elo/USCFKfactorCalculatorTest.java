package org.ametiste.elo.domain.elo;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by atlantis on 9/24/15.
 */
public class USCFKfactorCalculatorTest {

    private USCFKfactorCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new USCFKfactorCalculator();
    }

    @Test
    public void testGetFactorLower2100() throws Exception {
        assertEquals(32, calculator.getFactor(1999));
    }

    @Test
    public void testGetFactorLower2400() throws Exception {
        assertEquals(24,calculator.getFactor(2300));
    }

    @Test
    public void testGetFactorHigher2400() throws Exception {
        assertEquals(16,calculator.getFactor(2500));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFactorLowerZero() throws Exception {
        calculator.getFactor(-2500);
    }

    @Test
    public void testSameFactor() throws Exception {
        List<Integer> sameFactor = calculator.sameFactor(1999, Arrays.asList(2500, 2150, 200, 2000));
        assertArrayEquals(Arrays.asList(200, 2000).toArray(), sameFactor.toArray());
    }

    @Test
    public void testSameFactorNoFactor() throws Exception {
        List<Integer> sameFactor = calculator.sameFactor(1999, Arrays.asList(2500, 2150));
        assertArrayEquals(Collections.emptyList().toArray(), sameFactor.toArray());
    }
}