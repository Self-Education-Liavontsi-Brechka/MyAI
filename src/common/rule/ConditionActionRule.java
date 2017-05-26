package common.rule;

import common.action.Action;
import environment.state.DynamicEnvironmentState;

public class ConditionActionRule {
    private Condition condition;
    private Action action;

    public ConditionActionRule(Condition condition, Action action) {
        assert (null != condition) : "Condition must not be null in condition-action rule";
        assert (null != action) : "Action must not be null in condition-action rule";

        this.condition = condition;
        this.action = action;
    }

    public boolean evaluate(DynamicEnvironmentState state) {
        return condition.evaluate(state);
    }

    public Action getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof ConditionActionRule)) {
            return super.equals(o);
        }
        return (toString().equals(o.toString()));
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder().append("if ").append(condition).append(" then ").append(action)
                .append(".").toString();
    }
}
