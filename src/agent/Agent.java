package agent;

import common.action.Action;
import common.percept.Percept;
import environment.EnvironmentObject;

public interface Agent extends EnvironmentObject {
    Action execute(Percept percept);

    boolean isAlive();

    void setAlive(boolean isAlive);
}
