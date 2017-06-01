package search.common;

import common.action.Action;
import problem.Problem;

import java.util.List;
import java.util.function.Consumer;

public interface SearchForActions {
    List<Action> findActions(Problem problem);

    Metrics getMetrics();

    void addNodeListener(Consumer<Node> listener);

    boolean removeNodeListener(Consumer<Node> listener);
}
