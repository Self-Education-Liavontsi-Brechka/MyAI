package search.adversarial;

import common.action.Action;
import environment.state.EnvironmentState;
import search.common.Metrics;

public interface AdversarialSearch {
    Action makeDecision(EnvironmentState state);

    Metrics getMetrics();
}
