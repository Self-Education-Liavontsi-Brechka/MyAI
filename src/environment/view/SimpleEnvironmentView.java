package environment.view;

import agent.Agent;
import common.action.Action;
import common.percept.Percept;
import environment.Environment;

import java.io.PrintWriter;

public class SimpleEnvironmentView implements EnvironmentView {
    private PrintWriter out;

    public SimpleEnvironmentView(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void notify(String message) {
        out.println("Message: " + message);
    }

    @Override
    public void agentAdded(Agent agent, Environment source) {
        int agentId = source.getAgents().indexOf(agent) + 1;
        out.println("Agent " + agentId + " added.");
    }

    @Override
    public void agentActed(Agent agent, Percept percept, Action action, Environment source) {
        StringBuilder builder = new StringBuilder();
        int agentId = source.getAgents().indexOf(agent) + 1;

        builder.append("Agent ").append(agentId).append(" acted.")
                .append("\n   Percept: ").append(percept.toString())
                .append("\n   Action: ").append(action.toString());

        out.println(builder.toString());
    }
}
