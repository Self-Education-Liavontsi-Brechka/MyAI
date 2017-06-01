package search.queue;

import search.Node;
import search.NodeExpander;

public class TreeSearch extends QueueSearch {
    public TreeSearch() {
        this(new NodeExpander());
    }

    public TreeSearch(NodeExpander nodeExpander) {
        super(nodeExpander);
    }

    @Override
    protected void addToFrontier(Node node) {
        frontier.offer(node);
        updateQueueSizeMetric(frontier.size());
    }

    @Override
    protected Node removeFromFrontier() {
        Node removed = frontier.poll();
        updateQueueSizeMetric(frontier.size());
        return removed;
    }

    @Override
    protected boolean isFrontierEmpty() {
        return frontier.isEmpty();
    }
}
