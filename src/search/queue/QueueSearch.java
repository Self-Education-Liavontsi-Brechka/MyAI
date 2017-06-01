package search.queue;

import problem.Problem;
import search.Metrics;
import search.Node;
import search.NodeExpander;
import util.CancelableThread;
import util.SearchUtils;

import java.util.Queue;

public abstract class QueueSearch {
    public static final String METRIC_NODES_EXPANDED = "nodesExpanded";
    public static final String METRIC_QUEUE_SIZE = "queueSize";
    public static final String METRIC_MAX_QUEUE_SIZE = "maxQueueSize";
    public static final String METRIC_PATH_COST = "pathCost";

    final protected NodeExpander nodeExpander;
    protected Metrics metrics;
    protected Queue<Node> frontier;
    protected boolean earlyGoalTest;

    public QueueSearch(NodeExpander nodeExpander) {
        this.nodeExpander = nodeExpander;
        metrics = new Metrics();
        earlyGoalTest = false;

        nodeExpander.addNodeListener((node -> metrics.incrementInt(METRIC_NODES_EXPANDED)));
    }

    public void setEarlyGoalTest(boolean earlyGoalTest) {
        this.earlyGoalTest = earlyGoalTest;
    }

    public NodeExpander getNodeExpander() {
        return nodeExpander;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    protected abstract void addToFrontier(Node node);

    protected abstract Node removeFromFrontier();

    protected abstract boolean isFrontierEmpty();

    public Node findGoalNode(Problem problem, Queue<Node> frontier) {
        this.frontier = frontier;
        clearInstrumentation();

        Node root = nodeExpander.createRootNode(problem.getInitialState());
        addToFrontier(root);
        if (earlyGoalTest && SearchUtils.isGoalState(problem, root))
            return returnGoalNode(root);

        while (!isFrontierEmpty() && !CancelableThread.currIsCanceled()) {
            Node nodeToExpand = removeFromFrontier();
            if (!earlyGoalTest && SearchUtils.isGoalState(problem, nodeToExpand))
                return returnGoalNode(nodeToExpand);

            for (Node successor : nodeExpander.expand(nodeToExpand, problem)) {
                addToFrontier(successor);
                if (earlyGoalTest && SearchUtils.isGoalState(problem, successor))
                    return returnGoalNode(successor);
            }
        }

        return null;
    }

    public void clearInstrumentation() {
        metrics.set(METRIC_NODES_EXPANDED, 0);
        metrics.set(METRIC_QUEUE_SIZE, 0);
        metrics.set(METRIC_MAX_QUEUE_SIZE, 0);
        metrics.set(METRIC_PATH_COST, 0);
    }

    protected void updateQueueSizeMetric(int queueSize) {
        metrics.set(METRIC_QUEUE_SIZE, queueSize);
        int maxQSize = metrics.getInt(METRIC_MAX_QUEUE_SIZE);
        if (queueSize > maxQSize) {
            metrics.set(METRIC_MAX_QUEUE_SIZE, queueSize);
        }
    }

    private Node returnGoalNode(Node node) {
        metrics.set(METRIC_PATH_COST, node.getPathCost());
        return node;
    }
}
