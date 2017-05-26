package common.action;

public class NoOpAction extends DynamicAction {
    public static final NoOpAction INSTANCE = new NoOpAction();

    private NoOpAction() {
        super("NoOp");
    }

    @Override
    public boolean isNoOp() {
        return true;
    }
}