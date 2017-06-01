package search;

import common.action.Action;
import environment.state.EnvironmentState;
import problem.ActionsFunction;
import problem.Problem;
import problem.ResultFunction;
import problem.StepCostFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NodeExpander {
    protected boolean useParentLinks = true;
    private List<Consumer<Node>> nodeListeners = new ArrayList<>();

    public NodeExpander useParentLinks(boolean state) {
        useParentLinks = state;
        return this;
    }

    public Node createRootNode(EnvironmentState state) {
        return new Node(state);
    }

    public Node createNode(EnvironmentState state, Node parent, Action action, double stepCost) {
        return new Node(state, useParentLinks ? parent : null, action, parent.getPathCost() + stepCost);
    }

    public List<Node> expand(Node node, Problem problem) {
        List<Node> successors = new ArrayList<>();

        ActionsFunction actionsFunction = problem.getActionsFunction();
        ResultFunction resultFunction = problem.getResultFunction();
        StepCostFunction stepCostFunction = problem.getStepCostFunction();

        for (Action action : actionsFunction.actions(node.getState())) {
            EnvironmentState successorState = resultFunction.result(node.getState(), action);

            double stepCost = stepCostFunction.stepCost(node.getState(), action, successorState);
            successors.add(createNode(successorState, node, action, stepCost));
        }
        notifyNodeListeners(node);
        return successors;
    }

    public void addNodeListener(Consumer<Node> listener) {
        nodeListeners.add(listener);
    }

    public boolean removeNodeListener(Consumer<Node> listener) {
        return nodeListeners.remove(listener);
    }

    protected void notifyNodeListeners(Node node) {
        for (Consumer<Node> listener : nodeListeners)
            listener.accept(node);
    }
}
