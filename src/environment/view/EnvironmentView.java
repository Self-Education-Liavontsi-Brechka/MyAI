package environment.view;

import agent.Agent;
import common.action.Action;
import common.percept.Percept;
import environment.Environment;

public interface EnvironmentView {
    void notify(String message);

    void agentAdded(Agent agent, Environment source);

    void agentActed(Agent agent, Percept percept, Action action, Environment source);
}
