package agent.program;

import common.WorldModel;
import common.action.Action;
import common.action.NoOpAction;
import common.percept.Percept;
import common.rule.ConditionActionRule;
import environment.state.DynamicEnvironmentState;

import java.util.Set;

public abstract class ModelBasedReflexAgentProgram implements AgentProgram {
    private DynamicEnvironmentState internalState;
    private WorldModel worldModel;
    private Set<ConditionActionRule> rules;
    private Action recentAction;

    public ModelBasedReflexAgentProgram() {
        init();
    }

    public void setInternalState(DynamicEnvironmentState internalState) {
        this.internalState = internalState;
    }

    public void setWorldModel(WorldModel worldModel) {
        this.worldModel = worldModel;
    }

    public void setRules(Set<ConditionActionRule> rules) {
        this.rules = rules;
    }

    protected abstract void init();

    protected abstract DynamicEnvironmentState updateState(DynamicEnvironmentState internalState, Action action,
                                                           Percept percept, WorldModel model);

    protected ConditionActionRule ruleMatch(DynamicEnvironmentState internalState, Set<ConditionActionRule> rules) {
        for (ConditionActionRule rule : rules) {
            if (rule.evaluate(internalState)) {
                return rule;
            }
        }
        return null;
    }

    private Action ruleAction(ConditionActionRule rule) {
        return null == rule ? NoOpAction.INSTANCE : rule.getAction();
    }

    @Override
    public Action execute(Percept percept) {
        internalState = updateState(internalState, recentAction, percept, worldModel);
        ConditionActionRule rule = ruleMatch(internalState, rules);
        return recentAction = ruleAction(rule);
    }
}
