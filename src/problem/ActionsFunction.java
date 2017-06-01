package problem;

import common.action.Action;
import environment.state.EnvironmentState;

import java.util.Set;

public interface ActionsFunction {
    Set<Action> actions(EnvironmentState state);
}
