package demo.vacuum.environment;

import agent.Agent;
import common.action.Action;
import common.action.DynamicAction;
import common.percept.Percept;
import demo.vacuum.common.VacuumLocalPercept;
import environment.AbstractEnvironment;

public class VacuumEnvironment extends AbstractEnvironment {
    public static final Action ACTION_MOVE_LEFT = new DynamicAction("Left");
    public static final Action ACTION_MOVE_RIGHT = new DynamicAction("Right");
    public static final Action ACTION_SUCK = new DynamicAction("Suck");
    public static final String LOCATION_A = "A";
    public static final String LOCATION_B = "B";

    protected VacuumEnvironmentState state;
    protected boolean isDone;

    public VacuumEnvironment() {
        state = new VacuumEnvironmentState(LOCATION_A, LOCATION_B);
    }

    public VacuumEnvironmentState getState() {
        return state;
    }

    public void addAgent(Agent agent, String location) {
        state.setAgentLocation(agent, location);
        super.addAgent(agent);
    }

    @Override
    public void addAgent(Agent agent) {
        state.setAgentLocation(agent, Math.random() > 0.5 ? LOCATION_A : LOCATION_B);
        super.addAgent(agent);
    }

    @Override
    public void removeAgent(Agent agent) {
        state.removeAgentLocation(agent);
        super.removeAgent(agent);
    }

    @Override
    public boolean isDone() {
        return super.isDone() || isDone;
    }

    @Override
    public void executeAction(Agent agent, Action action) {
        if (ACTION_MOVE_LEFT == action) {
            state.setAgentLocation(agent, LOCATION_A);
            addPerformanceMeasureForAgent(agent, -1);
        } else if (ACTION_MOVE_RIGHT == action) {
            state.setAgentLocation(agent, LOCATION_B);
            addPerformanceMeasureForAgent(agent, -1);
        } else if (ACTION_SUCK == action) {
            String currentLocation = state.getAgentLocation(agent);
            if (state.getLocations().get(currentLocation) == VacuumEnvironmentState.LocationState.DIRTY) {
                state.getLocations().put(currentLocation, VacuumEnvironmentState.LocationState.CLEAN);
                addPerformanceMeasureForAgent(agent, 10);
            }
        } else if (action.isNoOp()) {
            isDone = true;
        }
    }

    @Override
    public Percept getPerceptForAgent(Agent agent) {
        String currentLocation = state.getAgentLocation(agent);
        return new VacuumLocalPercept(currentLocation, state.getLocations().get(currentLocation));
    }

    @Override
    public void createExogenousChange() {
    }
}
