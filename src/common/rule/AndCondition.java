package common.rule;

import environment.state.DynamicEnvironmentState;

public class AndCondition extends Condition {
    private Condition left;
    private Condition right;

    public AndCondition(Condition left, Condition right) {
        assert (null != left) : "Left condition must not be null";
        assert (null != right) : "Right condition must not be null";

        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(DynamicEnvironmentState state) {
        return left.evaluate(state) && right.evaluate(state);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[").append(left).append(" && ").append(right)
                .append("]").toString();
    }
}
