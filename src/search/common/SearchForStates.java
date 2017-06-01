package search.common;

import environment.state.EnvironmentState;
import problem.Problem;

import java.util.function.Consumer;

public interface SearchForStates {
    EnvironmentState findState(Problem problem);

    Metrics getMetrics();

    void addNodeListener(Consumer<Node> listener);

    boolean removeNodeListener(Consumer<Node> listener);
}
