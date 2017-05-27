package demo.vacuum.common;

import common.percept.DynamicPercept;
import demo.vacuum.environment.VacuumEnvironmentState;

public class VacuumLocalPercept extends DynamicPercept {
    public static final String AGENT_LOCATION_ATTRIBUTE = "agentLocation";
    public static final String LOCATION_STATE_ATTRIBUTE = "state";

    public VacuumLocalPercept(String agentLocation, VacuumEnvironmentState.LocationState locationState) {
        setAttribute(AGENT_LOCATION_ATTRIBUTE, agentLocation);
        setAttribute(LOCATION_STATE_ATTRIBUTE, locationState);
    }

    public String getAgentLocation() {
        return (String) getAttribute(AGENT_LOCATION_ATTRIBUTE);
    }

    public VacuumEnvironmentState.LocationState getLocationState() {
        return (VacuumEnvironmentState.LocationState) getAttribute(LOCATION_STATE_ATTRIBUTE);
    }

    @Override
    public String toString() {
        return new StringBuilder().append('{')
                .append(getAgentLocation())
                .append(',')
                .append(getLocationState())
                .append('}')
                .toString();
    }
}
