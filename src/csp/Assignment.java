package csp;

import csp.constraint.Constraint;

import java.util.*;

public class Assignment<VAR extends Variable, VAL> {
    private List<VAR> variables;
    private Map<VAR, VAL> variableToValue;

    public Assignment() {
        variables = new ArrayList<>();
        variableToValue = new Hashtable<>();
    }

    public List<VAR> getVariables() {
        return Collections.unmodifiableList(variables);
    }

    public VAL getValue(VAR variable) {
        return variableToValue.get(variable);
    }

    public void add(VAR variable, VAL value) {
        if (variableToValue.put(variable, value) == null) variables.add(variable);
    }

    public void remove(VAR variable) {
        if (contains(variable)) {
            variables.remove(variable);
            variableToValue.remove(variable);
        }
    }

    public boolean contains(VAR variable) {
        return variableToValue.get(variable) != null;
    }

    public boolean isConsistent(List<Constraint<VAR, VAL>> constraints) {
        for (Constraint<VAR, VAL> constraint : constraints)
            if (!constraint.isSatisfiedWithAssignment(this)) return false;
        return true;
    }

    public boolean isComplete(List<VAR> variables) {
        for (VAR variable : variables) if (!contains(variable)) return false;
        return true;
    }

    public boolean isSolution(CSP<VAR, VAL> csp) {
        return isConsistent(csp.getConstraints()) && isComplete(csp.getVariables());
    }

    public Assignment<VAR, VAL> copy() {
        Assignment<VAR, VAL> copy = new Assignment<>();
        for (VAR variable : variables) copy.add(variable, variableToValue.get(variable));
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("{");
        boolean comma = false;
        for (VAR variable : variables) {
            if (comma) res.append(", ");
            else comma = true;
            res.append(variable).append('=').append(variableToValue.get(variable));
        }
        return res.append('}').toString();
    }
}
