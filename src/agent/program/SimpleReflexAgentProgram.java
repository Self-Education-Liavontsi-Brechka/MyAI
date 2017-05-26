package agent.program;

import common.action.Action;
import common.action.NoOpAction;
import common.percept.Percept;
import common.rule.ConditionActionRule;
import environment.state.DynamicEnvironmentState;

import java.util.Set;

public abstract class SimpleReflexAgentProgram implements AgentProgram {
    private Set<ConditionActionRule> rules;

    public SimpleReflexAgentProgram() {
        init();
    }

    public void setRules(Set<ConditionActionRule> rules) {
        this.rules = rules;
    }

    protected abstract void init();

    protected abstract DynamicEnvironmentState interpretInput(Percept percept);

    protected ConditionActionRule ruleMatch(DynamicEnvironmentState state, Set<ConditionActionRule> rules) {
        for (ConditionActionRule rule : rules) {
            if (rule.evaluate(state)) {
                return rule;
            }
        }
        return null;
    }

    protected Action ruleAction(ConditionActionRule rule) {
        return null == rule ? NoOpAction.INSTANCE : rule.getAction();
    }

    @Override
    public Action execute(Percept percept) {
        DynamicEnvironmentState state = interpretInput(percept);
        ConditionActionRule rule = ruleMatch(state, rules);
        return ruleAction(rule);
    }
}
