package csp;

import csp.constraint.Constraint;

import java.util.*;

public class CSP<VAR extends Variable, VAL> {
    private List<VAR> variables;
    private List<Domain<VAL>> domains;
    private List<Constraint<VAR, VAL>> constraints;

    private Map<VAR, Integer> variableIndexHash;
    private Map<VAR, List<Constraint<VAR, VAL>>> constraintNetwork;

    public CSP() {
        variables = new ArrayList<>();
        domains = new ArrayList<>();
        constraints = new ArrayList<>();
        variableIndexHash = new Hashtable<>();
        constraintNetwork = new Hashtable<>();
    }

    public CSP(List<VAR> variables) {
        this();
        variables.forEach(this::addVariable);
    }

    public List<VAR> getVariables() {
        return Collections.unmodifiableList(variables);
    }

    public int indexOf(VAR variable) {
        return variableIndexHash.get(variable);
    }

    public void setDomain(VAR variable, Domain<VAL> domain) {
        domains.set(indexOf(variable), domain);
    }

    public Domain<VAL> getDomain(VAR variable) {
        return domains.get(variableIndexHash.get(variable));
    }

    public boolean removeValueFromDomain(VAR variable, VAL value) {
        Domain<VAL> currDomain = getDomain(variable);
        List<VAL> values = new ArrayList<>(currDomain.size());
        for (VAL v : currDomain)
            if (!v.equals(value))
                values.add(v);
        if (values.size() < currDomain.size()) {
            setDomain(variable, new Domain<>(values));
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public CSP<VAR, VAL> copyDomains() {
        CSP<VAR, VAL> result;
        try {
            result = (CSP<VAR, VAL>) clone();
            result.domains = new ArrayList<>(domains.size());
            result.domains.addAll(domains);
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException("Could not copy domains.");
        }
        return result;
    }

    public List<Constraint<VAR, VAL>> getConstraints() {
        return constraints;
    }

    public List<Constraint<VAR, VAL>> getConstraints(VAR variable) {
        return constraintNetwork.get(variable);
    }

    public void addConstraint(Constraint<VAR, VAL> constraint) {
        constraints.add(constraint);
        for (VAR variable : constraint.getScope())
            constraintNetwork.get(variable).add(constraint);
    }

    public boolean removeConstraint(Constraint<VAR, VAL> constraint) {
        boolean result = constraints.remove(constraint);
        if (result)
            for (VAR variable : constraint.getScope())
                constraintNetwork.get(variable).remove(constraint);
        return result;
    }

    public VAR getNeighbor(VAR var, Constraint<VAR, VAL> constraint) {
        List<VAR> scope = constraint.getScope();
        if (scope.size() == 2) {
            if (var.equals(scope.get(0)))
                return scope.get(1);
            else if (var.equals(scope.get(1)))
                return scope.get(0);
        }
        return null;
    }

    protected void addVariable(VAR variable) {
        if (!variableIndexHash.containsKey(variable)) {
            variables.add(variable);
            domains.add(new Domain<>());
            variableIndexHash.put(variable, variables.size() - 1);
            constraintNetwork.put(variable, new ArrayList<>());
        } else {
            throw new IllegalArgumentException("Variable with same name already exists.");
        }
    }
}
