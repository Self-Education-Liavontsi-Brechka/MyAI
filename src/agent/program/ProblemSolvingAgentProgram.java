package agent.program;

import common.action.Action;
import common.action.NoOpAction;
import common.percept.Percept;
import environment.state.EnvironmentState;
import problem.Problem;

import java.util.List;

public abstract class ProblemSolvingAgentProgram implements AgentProgram {
    private List<Action> actionSequence;
    private EnvironmentState internalState;
    private EnvironmentState goal;
    private Problem problem;

    public ProblemSolvingAgentProgram() {
        init();
    }

    protected abstract void init();

    protected abstract EnvironmentState updateState(EnvironmentState internalState, Percept percept);

    protected abstract EnvironmentState formulateGoal(EnvironmentState internalState);

    protected abstract Problem formulateProblem(EnvironmentState internalState, EnvironmentState goal);

    protected abstract List<Action> search(Problem problem);

    @Override
    public Action execute(Percept percept) {
        internalState = updateState(internalState, percept);
        if (actionSequence.size() == 0) {
            goal = formulateGoal(internalState);
            problem = formulateProblem(internalState, goal);
            List<Action> result = search(problem);
            if (result.size() == 0) {
                actionSequence.add(NoOpAction.INSTANCE);
            } else {
                actionSequence.addAll(result);
            }
        }

        if (actionSequence.size() > 0) return actionSequence.remove(0);
        else return NoOpAction.INSTANCE;
    }
}
