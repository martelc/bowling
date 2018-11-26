package martelc.bowling.crosscutting;

import com.google.inject.AbstractModule;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;
import martelc.bowling.domain.scoringstrategies.TenPinScoringStrategy;

public class DomainModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ScoringStrategy.class).to(TenPinScoringStrategy.class);
    }
}
