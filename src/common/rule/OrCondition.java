package common.rule;

import environment.state.DynamicEnvironmentState;

public class OrCondition extends Condition {
    private Condition left;
    private Condition right;

    public OrCondition(Condition left, Condition right) {
        assert (null != left);
        assert (null != right);

        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(DynamicEnvironmentState state) {
        return left.evaluate(state) || right.evaluate(state);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[").append(left).append(" || ").append(right)
                .append("]").toString();
    }

}
