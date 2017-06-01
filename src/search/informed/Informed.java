package search.informed;

import environment.state.EnvironmentState;

import java.util.function.Function;

public interface Informed {
    void setHeuristicFunction(Function<EnvironmentState, Double> heuristicFunction);
}
