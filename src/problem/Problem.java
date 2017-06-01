package problem;

import environment.state.EnvironmentState;

public class Problem {
    protected EnvironmentState initialState;
    protected ActionsFunction actionsFunction;
    protected ResultFunction resultFunction;
    protected GoalTestFunction goalTestFunction;
    protected StepCostFunction stepCostFunction;

    public Problem(EnvironmentState initialState,
                   ActionsFunction actionsFunction,
                   ResultFunction resultFunction,
                   GoalTestFunction goalTestFunction,
                   StepCostFunction stepCostFunction) {
        this.initialState = initialState;
        this.actionsFunction = actionsFunction;
        this.resultFunction = resultFunction;
        this.goalTestFunction = goalTestFunction;
        this.stepCostFunction = stepCostFunction;
    }

    public Problem(EnvironmentState initialState,
                   ActionsFunction actionsFunction,
                   ResultFunction resultFunction,
                   GoalTestFunction goalTestFunction) {
        this(initialState, actionsFunction, resultFunction, goalTestFunction, (s, a, sDelta) -> 1.0);
    }

    protected Problem() {

    }

    public EnvironmentState getInitialState() {
        return initialState;
    }

    public ActionsFunction getActionsFunction() {
        return actionsFunction;
    }

    public ResultFunction getResultFunction() {
        return resultFunction;
    }

    public GoalTestFunction getGoalTestFunction() {
        return goalTestFunction;
    }

    public StepCostFunction getStepCostFunction() {
        return stepCostFunction;
    }
}
