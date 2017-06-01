package problem;

import environment.state.EnvironmentState;

public interface GoalTestFunction {
    boolean isGoalState(EnvironmentState state);
}
