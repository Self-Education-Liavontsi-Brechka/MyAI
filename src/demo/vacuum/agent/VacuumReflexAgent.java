package demo.vacuum.agent;

import agent.AbstractAgent;
import agent.program.ReflexAgentProgram;
import common.percept.Percept;
import common.rule.ConditionActionRule;
import common.rule.EqualCondition;
import demo.vacuum.common.VacuumLocalPercept;
import demo.vacuum.environment.VacuumEnvironment;
import demo.vacuum.environment.VacuumEnvironmentState;
import environment.state.DynamicEnvironmentState;

import java.util.HashSet;
import java.util.Set;

public class VacuumReflexAgent extends AbstractAgent {
    public VacuumReflexAgent() {
        super(new ReflexAgentProgram() {
            @Override
            protected void init() {
                Set<ConditionActionRule> rules = new HashSet<>();

                rules.add(new ConditionActionRule(
                        new EqualCondition(VacuumLocalPercept.LOCATION_STATE_ATTRIBUTE,
                                VacuumEnvironmentState.LocationState.DIRTY),
                        VacuumEnvironment.ACTION_SUCK
                ));
                rules.add(new ConditionActionRule(
                        new EqualCondition(VacuumLocalPercept.AGENT_LOCATION_ATTRIBUTE, VacuumEnvironment.LOCATION_A),
                        VacuumEnvironment.ACTION_MOVE_RIGHT
                ));
                rules.add(new ConditionActionRule(
                        new EqualCondition(VacuumLocalPercept.AGENT_LOCATION_ATTRIBUTE, VacuumEnvironment.LOCATION_B),
                        VacuumEnvironment.ACTION_MOVE_LEFT
                ));

                setRules(rules);
            }

            @Override
            protected DynamicEnvironmentState interpretInput(Percept percept) {
                VacuumLocalPercept localPercept = (VacuumLocalPercept) percept;
                DynamicEnvironmentState state = new DynamicEnvironmentState();

                state.setAttribute(VacuumLocalPercept.AGENT_LOCATION_ATTRIBUTE, localPercept.getAgentLocation());
                state.setAttribute(VacuumLocalPercept.LOCATION_STATE_ATTRIBUTE, localPercept.getLocationState());

                return state;
            }
        });
    }
}
