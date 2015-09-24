package org.ametiste.elo.infrastructure;

import org.ametiste.elo.infrastructure.dto.MatchDto;
import org.ametiste.elo.infrastructure.dto.PlayerDto;

import java.util.List;

/**
 * Created by atlantis on 9/24/15.
 */
public interface MatchesRepository {

    List<PlayerDto> loadPlayers();

    List<MatchDto> loadMatches();
}
