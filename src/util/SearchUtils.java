package util;

import common.action.Action;
import common.action.NoOpAction;
import problem.GoalTestFunction;
import problem.Problem;
import search.common.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchUtils {
    public static List<Action> getSequenceOfActions(Node node) {
        List<Node> nodes = node.getPathFromRoot();
        List<Action> actions = new ArrayList<>();
        if (nodes.size() == 1) {
            actions.add(NoOpAction.INSTANCE);
        } else {
            for (int i = 1; i < nodes.size(); i++) actions.add(nodes.get(i).getAction());
        }
        return actions;
    }

    public static List<Action> failure() {
        return Collections.emptyList();
    }

    public static boolean isFailure(List<Action> actions) {
        return actions.isEmpty();
    }

    public static boolean isGoalState(Problem problem, Node node) {
        boolean isGoal = false;
        GoalTestFunction goalTest = problem.getGoalTestFunction();
        if (goalTest.isGoalState(node.getState())) {
            if (goalTest instanceof SolutionChecker) {
                isGoal = ((SolutionChecker) goalTest).isAcceptableSolution(getSequenceOfActions(node), node.getState());
            } else {
                isGoal = true;
            }
        }
        return isGoal;
    }

    public interface SolutionChecker extends GoalTestFunction {
        boolean isAcceptableSolution(List<Action> actions, Object goal);
    }
}