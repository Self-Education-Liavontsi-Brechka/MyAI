package search.local;

import environment.state.EnvironmentState;
import problem.Problem;
import search.common.Node;
import search.common.NodeExpander;
import search.common.SearchOutcome;
import util.CancelableThread;
import util.RandomUtil;
import util.SearchUtils;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class SimulatedAnnealingSearch extends LocalSearch {
    public static final String METRIC_TEMPERATURE = "temp";

    private final Scheduler scheduler;

    public SimulatedAnnealingSearch(Function<EnvironmentState, Double> heuristicFunction) {
        this(new Scheduler(), heuristicFunction);
    }

    public SimulatedAnnealingSearch(Scheduler scheduler, Function<EnvironmentState, Double> heuristicFunction) {
        this(new NodeExpander(), scheduler, heuristicFunction);
    }

    public SimulatedAnnealingSearch(NodeExpander nodeExpander,
                                    Scheduler scheduler,
                                    Function<EnvironmentState, Double> heuristicFunction) {
        super(nodeExpander, heuristicFunction);
        this.scheduler = scheduler;
    }

    private boolean shouldAccept(double temperature, double deltaE) {
        return deltaE > 0.0 || new Random().nextDouble() <= Math.exp(deltaE / temperature);
    }

    @Override
    public Node findSolutionNode(Problem problem) {
        clearInstrumentation();
        outcome = SearchOutcome.FAILURE;
        lastFoundState = null;

        Node current = nodeExpander.createRootNode(problem.getInitialState());
        Node next;
        List<Node> successors;
        double temperature, deltaE;
        for (int t = 0; !CancelableThread.currIsCanceled(); t++) {
            lastFoundState = current.getState();
            temperature = scheduler.getTemperature(t);
            metrics.set(METRIC_TEMPERATURE, temperature);
            metrics.set(METRIC_NODE_VALUE, getResultValue(current));

            if (temperature == 0.0) {
                if (SearchUtils.isGoalState(problem, current)) outcome = SearchOutcome.SOLUTION_FOUND;
                return current;
            }

            successors = nodeExpander.expand(current, problem);
            if (successors.size() > 0) {
                next = RandomUtil.selectRandomlyFromList(successors);
                deltaE = getResultValue(next) - getResultValue(current);

                if (shouldAccept(temperature, deltaE)) current = next;
            }
        }
        return null;
    }
}
