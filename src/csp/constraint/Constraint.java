package csp.constraint;

import csp.Assignment;
import csp.Variable;

import java.util.List;

public interface Constraint<VAR extends Variable, VAL> {
    List<VAR> getScope();

    boolean isSatisfiedWithAssignment(Assignment<VAR, VAL> assignment);
}
