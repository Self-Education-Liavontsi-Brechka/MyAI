package demo.vacuum.agent;

import agent.AbstractAgent;
import agent.program.ModelBasedReflexAgentProgram;
import common.WorldModel;
import common.action.Action;
import common.action.NoOpAction;
import common.percept.Percept;
import common.rule.AndCondition;
import common.rule.ConditionActionRule;
import common.rule.EqualCondition;
import demo.vacuum.common.VacuumLocalPercept;
import demo.vacuum.environment.VacuumEnvironment;
import demo.vacuum.environment.VacuumEnvironmentState;
import environment.state.DynamicEnvironmentState;

import java.util.LinkedHashSet;
import java.util.Set;

public class VacuumModelBasedReflexAgent extends AbstractAgent {
    private static final String ATTRIBUTE_CURRENT_LOCATION = "currentLocation";
    private static final String ATTRIBUTE_CURRENT_STATE = "currentState";
    private static final String ATTRIBUTE_STATE_LOCATION_A = "stateLocationA";
    private static final String ATTRIBUTE_STATE_LOCATION_B = "stateLocationB";

    public VacuumModelBasedReflexAgent() {
        super(new ModelBasedReflexAgentProgram() {
            @Override
            protected void init() {
                setInternalState(new DynamicEnvironmentState());

                Set<ConditionActionRule> rules = new LinkedHashSet<>();

                rules.add(new ConditionActionRule(new AndCondition(
                        new EqualCondition(ATTRIBUTE_STATE_LOCATION_A, VacuumEnvironmentState.LocationState.CLEAN),
                        new EqualCondition(ATTRIBUTE_STATE_LOCATION_B, VacuumEnvironmentState.LocationState.CLEAN)),
                        NoOpAction.INSTANCE));
                rules.add(new ConditionActionRule(
                        new EqualCondition(ATTRIBUTE_CURRENT_STATE, VacuumEnvironmentState.LocationState.DIRTY),
                        VacuumEnvironment.ACTION_SUCK));
                rules.add(new ConditionActionRule(
                        new EqualCondition(ATTRIBUTE_CURRENT_LOCATION, VacuumEnvironment.LOCATION_A),
                        VacuumEnvironment.ACTION_MOVE_RIGHT));
                rules.add(new ConditionActionRule(new EqualCondition(ATTRIBUTE_CURRENT_LOCATION,
                        VacuumEnvironment.LOCATION_B),
                        VacuumEnvironment.ACTION_MOVE_LEFT));

                setRules(rules);
            }

            @Override
            protected DynamicEnvironmentState updateState(DynamicEnvironmentState internalState, Action action,
                                                          Percept percept, WorldModel model) {
                VacuumLocalPercept localPercept = (VacuumLocalPercept) percept;

                internalState.setAttribute(ATTRIBUTE_CURRENT_LOCATION, localPercept.getAgentLocation());
                internalState.setAttribute(ATTRIBUTE_CURRENT_STATE, localPercept.getLocationState());

                if (localPercept.getAgentLocation().equals(VacuumEnvironment.LOCATION_A)) {
                    internalState.setAttribute(ATTRIBUTE_STATE_LOCATION_A, localPercept.getLocationState());
                } else {
                    internalState.setAttribute(ATTRIBUTE_STATE_LOCATION_B, localPercept.getLocationState());
                }

                return internalState;
            }
        });
    }
}
