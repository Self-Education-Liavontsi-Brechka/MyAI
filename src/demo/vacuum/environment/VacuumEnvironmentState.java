package demo.vacuum.environment;

import agent.Agent;
import environment.state.EnvironmentState;

import java.util.LinkedHashMap;
import java.util.Map;

public class VacuumEnvironmentState implements EnvironmentState {
    private Map<String, LocationState> locations;
    private Map<Agent, String> agentLocations;

    public VacuumEnvironmentState(String... locationNames) {
        locations = new LinkedHashMap<>();
        agentLocations = new LinkedHashMap<>();

        for (String location : locationNames) {
            locations.put(location, Math.random() > 0.5 ? LocationState.CLEAN : LocationState.DIRTY);
        }
    }

    public Map<String, LocationState> getLocations() {
        return locations;
    }

    public String getAgentLocation(Agent agent) {
        return agentLocations.get(agent);
    }

    public void setAgentLocation(Agent agent, String location) {
        agentLocations.put(agent, location);
    }

    public void removeAgentLocation(Agent agent) {
        agentLocations.remove(agent);
    }


//    private void initLocations(int number) {
//        StringBuilder nextName;
//        for (int i = 1, next; i <= number; i++) {
//            next = i;
//            nextName = new StringBuilder();
//            while (next > 0) {
//                nextName.append((char) ('A' + ((next - 1) % 26)));
//                next = (next - 1) / 26;
//            }
//            locations.add(new Location(nextName.reverse().toString(), i - 1,
//                    Math.random() > 0.5 ? Location.LocationState.CLEAN : Location.LocationState.DIRTY));
//        }
//    }

    public enum LocationState {
        CLEAN, DIRTY
    }

//    public static class Location {
//        private String name;
//        private int x;
//        private LocationState state;
//
//        private Location(String name, int x, LocationState state) {
//            this.name = name;
//            this.x = x;
//            this.state = state;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public int getX() {
//            return x;
//        }
//
//        public LocationState getState() {
//            return state;
//        }
//
//        public void setState(LocationState state) {
//            this.state = state;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//
//            Location location = (Location) o;
//
//            return name != null ? name.equals(location.name) : location.name == null;
//        }
//
//        @Override
//        public int hashCode() {
//            return name != null ? name.hashCode() : 0;
//        }
//
//        @Override
//        public String toString() {
//            return new StringBuilder()
//                    .append("{Name: ").append(name)
//                    .append(", X: ").append(x)
//                    .append(", State: ").append(state.name())
//                    .append("}").toString();
//        }
//
//        public enum LocationState {
//            CLEAN, DIRTY
//        }
//    }
}
