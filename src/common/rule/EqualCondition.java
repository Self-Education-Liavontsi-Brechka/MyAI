package common.rule;

import environment.state.DynamicEnvironmentState;

public class EqualCondition extends Condition {
    private Object key;
    private Object value;

    public EqualCondition(Object key, Object value) {
        assert (null != key);
        assert (null != value);

        this.key = key;
        this.value = value;
    }

    @Override
    public boolean evaluate(DynamicEnvironmentState state) {
        return value.equals(state.getAttribute(key));
    }

    @Override
    public String toString() {
        return new StringBuilder().append(key).append("==").append(value).toString();
    }
}
