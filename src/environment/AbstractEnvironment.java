package environment;

import agent.Agent;
import common.action.Action;
import common.percept.Percept;
import environment.view.EnvironmentView;
import environment.view.EnvironmentViewNotifier;

import java.util.*;

public abstract class AbstractEnvironment implements Environment, EnvironmentViewNotifier {
    protected Set<EnvironmentObject> environmentObjects;
    protected Set<Agent> agents;
    protected Set<EnvironmentView> views;
    protected Map<Agent, Double> performanceMeasures;

    // TODO: consider actuators and sensors

    public AbstractEnvironment() {
        this.environmentObjects = new LinkedHashSet<>();
        this.agents = new LinkedHashSet<>();
        this.views = new LinkedHashSet<>();
        this.performanceMeasures = new LinkedHashMap<>();
    }

    public abstract void executeAction(Agent agent, Action action);

    public abstract Percept getPerceptForAgent(Agent agent);

    public abstract void createExogenousChange();

    protected void notifyEnvironmentViews(Agent agent) {
        for (EnvironmentView view : views) {
            view.agentAdded(agent, this);
        }
    }

    protected void notifyEnvironmentViews(Agent agent, Percept percept, Action action) {
        for (EnvironmentView view : views) {
            view.agentActed(agent, percept, action, this);
        }
    }

    @Override
    public List<Agent> getAgents() {
        return new ArrayList<>(agents);
    }

    @Override
    public void addAgent(Agent agent) {
        addEnvironmentObject(agent);
    }

    @Override
    public void removeAgent(Agent agent) {
        removeEnvironmentObject(agent);
    }

    @Override
    public List<EnvironmentObject> getEnvironmentObjects() {
        return new ArrayList<>(environmentObjects);
    }

    @Override
    public void addEnvironmentObject(EnvironmentObject object) {
        environmentObjects.add(object);
        if (object instanceof Agent) {
            Agent agent = (Agent) object;
            if (!agents.contains(agent)) {
                agents.add(agent);
                notifyEnvironmentViews(agent);
            }
        }
    }

    @Override
    public void removeEnvironmentObject(EnvironmentObject object) {
        environmentObjects.remove(object);
        if (object instanceof Agent) {
            agents.remove(object);
        }
    }

    @Override
    public void step() {
        for (Agent agent : agents) {
            if (agent.isAlive()) {
                Percept percept = getPerceptForAgent(agent);
                Action action = agent.execute(percept);
                executeAction(agent, action);
                notifyEnvironmentViews(agent, percept, action);
            }
        }
        createExogenousChange();
    }

    @Override
    public void step(int n) {
        for (int i = 0; i < n; i++) {
            step();
        }
    }

    @Override
    public void stepUntilDone() {
        while (!isDone()) {
            step();
        }
    }

    @Override
    public boolean isDone() {
        for (Agent agent : agents) {
            if (agent.isAlive()) return false;
        }
        return true;
    }

    @Override
    public void addPerformanceMeasureForAgent(Agent agent, double value) {
        performanceMeasures.put(agent, getPerformanceMeasureForAgent(agent) + value);
    }

    @Override
    public double getPerformanceMeasureForAgent(Agent agent) {
        return performanceMeasures.computeIfAbsent(agent, key -> 0.0D);
    }

    @Override
    public void addEnvironmentView(EnvironmentView environmentView) {
        views.add(environmentView);
    }

    @Override
    public void removeEnvironmentView(EnvironmentView environmentView) {
        views.remove(environmentView);
    }

    @Override
    public void notifyViews(String message) {
        for (EnvironmentView view : views) {
            view.notify(message);
        }
    }
}
