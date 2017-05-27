package demo.vacuum;

import agent.Agent;
import demo.vacuum.agent.VacuumModelBasedReflexAgent;
import demo.vacuum.environment.VacuumEnvironment;
import environment.Environment;
import environment.view.SimpleEnvironmentView;

import java.io.PrintWriter;

public class VacuumDemo {
    public static void main(String[] args) {
        Environment environment = new VacuumEnvironment();
        environment.addEnvironmentView(new SimpleEnvironmentView(new PrintWriter(System.out, true)));

//        Agent agent = new VacuumReflexAgent();
        Agent agent = new VacuumModelBasedReflexAgent();

        environment.addAgent(agent);
        environment.stepUntilDone();
//        environment.stepUntilDone();
        environment.notifyViews("Performance=" + environment.getPerformanceMeasureForAgent(agent));
    }
}
