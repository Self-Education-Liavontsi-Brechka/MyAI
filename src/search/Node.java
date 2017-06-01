package search;

import common.action.Action;
import common.action.NoOpAction;
import environment.state.EnvironmentState;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private EnvironmentState state;
    private Node parent;
    private Action action;
    private double pathCost;

    public Node(EnvironmentState state) {
        this.state = state;
        pathCost = 0.0;
        action = NoOpAction.INSTANCE;
    }

    public Node(EnvironmentState state, Node parent, Action action, double pathCost) {
        this(state);
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;
    }

    public EnvironmentState getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public Action getAction() {
        return action;
    }

    public double getPathCost() {
        return pathCost;
    }

    public boolean isRootNode() {
        return parent == null;
    }

    public List<Node> getPathFromRoot() {
        List<Node> path = new ArrayList<>();
        Node current = this;
        while (!current.isRootNode()) {
            path.add(0, current);
            current = current.getParent();
        }
        // ensure the root node is added
        path.add(0, current);
        return path;
    }

    @Override
    public String toString() {
        return "{parent=" + parent +
                ", action=" + action +
                ", state=" + state +
                ", pathCost=" + pathCost +
                "}";
    }
}
