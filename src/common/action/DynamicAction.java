package common.action;

import util.ObjectWithDynamicAttributes;

public class DynamicAction extends ObjectWithDynamicAttributes implements Action {
    private static final String NAME_ATTRIBUTE = "name";

    public DynamicAction(String name) {
        setAttribute(NAME_ATTRIBUTE, name);
    }

    public String getName() {
        return (String) getAttribute(NAME_ATTRIBUTE);
    }

    @Override
    public String getTypeDescription() {
        return Action.class.getSimpleName();
    }

    @Override
    public boolean isNoOp() {
        return false;
    }
}
