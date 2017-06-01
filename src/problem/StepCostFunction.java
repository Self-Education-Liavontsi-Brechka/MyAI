package problem;

import common.action.Action;
import environment.state.EnvironmentState;

public interface StepCostFunction {
    double stepCost(EnvironmentState state, Action action, EnvironmentState stateDelta);
}
