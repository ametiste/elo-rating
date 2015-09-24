package org.ametiste.elo.application;

import org.ametiste.elo.domain.RatingStore;
import org.ametiste.elo.domain.model.Player;
import org.ametiste.elo.infrastructure.MatchesRepository;
import org.ametiste.elo.infrastructure.dto.MatchDto;
import org.ametiste.elo.infrastructure.dto.PlayerDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by atlantis on 9/25/15.
 */
public class EloRatingAppTest {

    private EloRatingApp app;

    @Mock
    private MatchesRepository repository;

    @Mock
    private RatingStore store;
    private List<PlayerDto> playersDto;

    private List<MatchDto> matchDto;
    private List<Player> players = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        app = new EloRatingApp(repository, store);
        playersDto = Arrays.asList(new PlayerDto(1, "Name1"), new PlayerDto(2, "Name2"));
        matchDto = Arrays.asList(new MatchDto(1, 2), new MatchDto(2, 1));
    }

    @Test
    public void testCalculate() throws Exception {
        when(repository.loadPlayers()).thenReturn(playersDto);
        when(repository.loadMatches()).thenReturn(matchDto);
        app.calculate();
        verify(repository, times(1)).loadMatches();
        verify(repository, times(1)).loadPlayers();
        verify(store, times(1)).init(anyList());
        verify(store, times(2)).updateRating(anyInt(), anyInt());
    }

    @Test
    public void testSortPlayersByRanking() throws Exception {
        ArgumentCaptor<Comparator> arg = ArgumentCaptor.forClass(Comparator.class);
        when(store.loadSorted(arg.capture())).thenReturn(players);
        app.sortPlayersByRanking();
        verify(store).loadSorted(arg.getValue());
    }

    @Test
    public void testGetPlayerInfo() throws Exception {
        app.getPlayerInfo(2);
        verify(store, times(1)).getPlayerById(2);
    }

    @Test
    public void testGenerateMatches() throws Exception {
        app.generateMatches(6);
        verify(store, times(1)).getFutureMatches(6);
    }
}