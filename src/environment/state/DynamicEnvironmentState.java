package environment.state;

import util.ObjectWithDynamicAttributes;

public class DynamicEnvironmentState extends ObjectWithDynamicAttributes implements EnvironmentState {
    @Override
    public String getTypeDescription() {
        return EnvironmentState.class.getSimpleName();
    }
}
