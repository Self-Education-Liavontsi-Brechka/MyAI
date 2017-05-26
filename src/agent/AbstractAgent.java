package agent;

import agent.program.AgentProgram;
import common.action.Action;
import common.action.NoOpAction;
import common.percept.Percept;

public abstract class AbstractAgent implements Agent {
    protected AgentProgram program;
    private boolean isAlive;

    public AbstractAgent(AgentProgram program) {
        this.isAlive = true;
        this.program = program;
    }

    @Override
    public Action execute(Percept percept) {
        if (this.program != null) {
            return this.program.execute(percept);
        }
        return NoOpAction.INSTANCE;
    }

    @Override
    public boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}
