package search.queue;

import environment.state.EnvironmentState;
import problem.Problem;
import search.Node;
import search.NodeExpander;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class GraphSearch extends QueueSearch {
    Set<EnvironmentState> explored;

    public GraphSearch() {
        this(new NodeExpander());
    }

    public GraphSearch(NodeExpander nodeExpander) {
        super(nodeExpander);
        explored = new HashSet<>();
    }

    @Override
    public Node findGoalNode(Problem problem, Queue<Node> frontier) {
        explored.clear();
        return super.findGoalNode(problem, frontier);
    }

    @Override
    protected void addToFrontier(Node node) {
        if (!explored.contains(node.getState())) {
            frontier.offer(node);
            updateQueueSizeMetric(frontier.size());
        }
    }

    @Override
    protected Node removeFromFrontier() {
        cleanUpFrontier();
        Node removed = frontier.poll();
        explored.add(removed.getState());
        updateQueueSizeMetric(frontier.size());
        return removed;
    }

    @Override
    protected boolean isFrontierEmpty() {
        cleanUpFrontier();
        updateQueueSizeMetric(frontier.size());
        return frontier.isEmpty();
    }

    private void cleanUpFrontier() {
        while (!frontier.isEmpty() && explored.contains(frontier.peek().getState()))
            frontier.poll();
    }
}
