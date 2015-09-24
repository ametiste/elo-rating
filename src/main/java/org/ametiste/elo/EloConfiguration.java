package org.ametiste.elo;

import org.ametiste.elo.application.EloRatingApp;
import org.ametiste.elo.domain.RatingStore;
import org.ametiste.elo.domain.MapRatingStore;
import org.ametiste.elo.domain.RankingCalculator;
import org.ametiste.elo.domain.elo.EloRankingCalculator;
import org.ametiste.elo.domain.elo.USCFKfactorCalculator;
import org.ametiste.elo.infrastructure.MatchesRepository;
import org.ametiste.elo.infrastructure.fs.FileMatchesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by atlantis on 9/24/15.
 */
@Configuration
public class EloConfiguration {

    @Value("${org.ametiste.elo.matches.file.path}")
    private String matchesFilePath;

    @Value("${org.ametiste.elo.names.file.path}")
    private String namesFilePath;

    @Bean(initMethod = "calculate")
    public EloRatingApp ratingApp() {
        return new EloRatingApp(matchesRepository(), ratingStore());
    }

    @Bean
    public  MatchesRepository matchesRepository() {
        return new FileMatchesRepository(namesFilePath,matchesFilePath);
    }

    @Bean
    public RatingStore ratingStore() {
        return new MapRatingStore(calculator());
    }

    @Bean
    public RankingCalculator calculator() {
        return new EloRankingCalculator(new USCFKfactorCalculator());
    }
}
