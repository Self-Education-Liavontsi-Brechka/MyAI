package agent.program;

import common.action.Action;
import common.percept.Percept;

public interface AgentProgram {
    Action execute(Percept percept);
}
