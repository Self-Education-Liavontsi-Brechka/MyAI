package common.rule;

import environment.state.DynamicEnvironmentState;

public abstract class Condition {
    public abstract boolean evaluate(DynamicEnvironmentState state);

    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass() && toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
