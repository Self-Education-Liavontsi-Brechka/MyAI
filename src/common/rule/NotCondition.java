package common.rule;

import environment.state.DynamicEnvironmentState;

public class NotCondition extends Condition {
    private Condition condition;

    public NotCondition(Condition condition) {
        assert (null != condition);

        this.condition = condition;
    }

    @Override
    public boolean evaluate(DynamicEnvironmentState state) {
        return !condition.evaluate(state);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("![").append(condition).append("]").toString();
    }
}
