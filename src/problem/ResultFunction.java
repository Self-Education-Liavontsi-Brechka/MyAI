package problem;

import common.action.Action;
import environment.state.EnvironmentState;

public interface ResultFunction {
    EnvironmentState result(EnvironmentState state, Action action);
}
