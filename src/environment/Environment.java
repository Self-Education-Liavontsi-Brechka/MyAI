package environment;

import agent.Agent;
import environment.view.EnvironmentView;

import java.util.List;

public interface Environment {
    List<Agent> getAgents();

    void addAgent(Agent agent);

    void removeAgent(Agent agent);

    List<EnvironmentObject> getEnvironmentObjects();

    void addEnvironmentObject(EnvironmentObject object);

    void removeEnvironmentObject(EnvironmentObject object);

    void step();

    void step(int n);

    void stepUntilDone();

    boolean isDone();

    void addPerformanceMeasureForAgent(Agent agent, double value);

    double getPerformanceMeasureForAgent(Agent agent);

    void addEnvironmentView(EnvironmentView environmentView);

    void removeEnvironmentView(EnvironmentView environmentView);

    void notifyViews(String message);
}
