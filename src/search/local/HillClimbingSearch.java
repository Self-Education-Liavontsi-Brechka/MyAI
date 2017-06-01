package search.local;

import environment.state.EnvironmentState;
import problem.Problem;
import search.common.Node;
import search.common.NodeExpander;
import search.common.SearchOutcome;
import util.CancelableThread;
import util.SearchUtils;

import java.util.List;
import java.util.function.Function;

public class HillClimbingSearch extends LocalSearch {
    public HillClimbingSearch(Function<EnvironmentState, Double> heuristicFunction) {
        super(heuristicFunction);
    }

    public HillClimbingSearch(NodeExpander nodeExpander, Function<EnvironmentState, Double> heuristicFunction) {
        super(nodeExpander, heuristicFunction);
    }

    private Node getHighestValuedNodeFrom(List<Node> successors) {
        double highestValue = Double.NEGATIVE_INFINITY;
        Node nodeWithHighestValue = null;
        for (Node successor : successors) {
            double value = getResultValue(successor);
            if (value > highestValue) {
                highestValue = value;
                nodeWithHighestValue = successor;
            }
        }
        return nodeWithHighestValue;
    }

    @Override
    public Node findSolutionNode(Problem problem) {
        clearInstrumentation();
        outcome = SearchOutcome.FAILURE;
        lastFoundState = null;

        Node current = nodeExpander.createRootNode(problem.getInitialState());
        Node neighbor;
        List<Node> successors;
        while (!CancelableThread.currIsCanceled()) {
            lastFoundState = current.getState();
            metrics.set(METRIC_NODE_VALUE, getResultValue(current));

            successors = nodeExpander.expand(current, problem);
            neighbor = getHighestValuedNodeFrom(successors);
            if ((neighbor == null) || (getResultValue(neighbor) <= getResultValue(current))) {
                if (SearchUtils.isGoalState(problem, current))
                    outcome = SearchOutcome.SOLUTION_FOUND;
                return current;
            }
            current = neighbor;
        }
        return null;
    }
}
