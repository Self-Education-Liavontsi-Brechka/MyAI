package search.local;

import common.action.Action;
import environment.state.EnvironmentState;
import problem.Problem;
import search.common.*;
import search.informed.Informed;
import util.SearchUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class LocalSearch implements SearchForActions, SearchForStates, Informed {
    public static final String METRIC_NODES_EXPANDED = "nodesExpanded";
    public static final String METRIC_NODE_VALUE = "nodeValue";

    protected final NodeExpander nodeExpander;
    protected Function<EnvironmentState, Double> heuristicFunction;

    protected Metrics metrics;
    protected SearchOutcome outcome;
    protected EnvironmentState lastFoundState;

    public LocalSearch(Function<EnvironmentState, Double> heuristicFunction) {
        this(new NodeExpander(), heuristicFunction);
    }

    public LocalSearch(NodeExpander nodeExpander, Function<EnvironmentState, Double> heuristicFunction) {
        metrics = new Metrics();
        outcome = SearchOutcome.FAILURE;
        this.nodeExpander = nodeExpander;
        this.heuristicFunction = heuristicFunction;
        this.nodeExpander.addNodeListener(node -> metrics.incrementInt(METRIC_NODES_EXPANDED));
    }

    public SearchOutcome getOutcome() {
        return outcome;
    }

    public EnvironmentState getLastFoundState() {
        return lastFoundState;
    }

    public abstract Node findSolutionNode(Problem problem);

    protected void clearInstrumentation() {
        metrics.set(METRIC_NODES_EXPANDED, 0);
        metrics.set(METRIC_NODE_VALUE, 0);
    }

    protected double getResultValue(Node node) {
        // assumption greater heuristic value =>
        // HIGHER on hill; 0 == goal state;
        return -1.0 * heuristicFunction.apply(node.getState());
    }

    @Override
    public List<Action> findActions(Problem problem) {
        nodeExpander.useParentLinks(true);
        Node finalNode = findSolutionNode(problem);
        return finalNode == null ? SearchUtils.failure() : SearchUtils.getSequenceOfActions(finalNode);
    }

    @Override
    public EnvironmentState findState(Problem problem) {
        nodeExpander.useParentLinks(false);
        Node finalNode = findSolutionNode(problem);
        return finalNode == null ? null : finalNode.getState();
    }

    @Override
    public Metrics getMetrics() {
        return metrics;
    }

    @Override
    public void addNodeListener(Consumer<Node> listener) {
        nodeExpander.addNodeListener(listener);
    }

    @Override
    public boolean removeNodeListener(Consumer<Node> listener) {
        return nodeExpander.removeNodeListener(listener);
    }

    @Override
    public void setHeuristicFunction(Function<EnvironmentState, Double> heuristicFunction) {
        this.heuristicFunction = heuristicFunction;
    }
}
