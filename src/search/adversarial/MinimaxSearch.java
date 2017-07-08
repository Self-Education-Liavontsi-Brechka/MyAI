package search.adversarial;

import common.action.Action;
import common.action.NoOpAction;
import environment.state.EnvironmentState;
import search.common.Metrics;

public class MinimaxSearch implements AdversarialSearch {
    public final static String METRICS_NODES_EXPANDED = "nodesExpanded";

    private Game game;
    private Metrics metrics;

    public MinimaxSearch(Game game) {
        this.game = game;
        metrics = new Metrics();
    }

    private double maxValue(EnvironmentState state, Player player) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);

        if (game.isTerminal(state)) return game.getUtility(state, player);

        double value = Double.NEGATIVE_INFINITY;
        for (Action action : game.getActions(state))
            value = Math.max(value, minValue(game.getResult(state, action), player));

        return value;
    }

    private double minValue(EnvironmentState state, Player player) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);

        if (game.isTerminal(state)) return game.getUtility(state, player);

        double value = Double.POSITIVE_INFINITY;
        for (Action action : game.getActions(state))
            value = Math.min(value, maxValue(game.getResult(state, action), player));

        return value;
    }

    @Override
    public Action makeDecision(EnvironmentState state) {
        metrics = new Metrics();

        Action result = NoOpAction.INSTANCE;
        double resultValue = Double.NEGATIVE_INFINITY;
        Player player = game.getPlayer(state);
        double value;

        for (Action action : game.getActions(state)) {
            value = minValue(game.getResult(state, action), player);
            if (value > resultValue) {
                resultValue = value;
                result = action;
            }
        }

        return result;
    }

    @Override
    public Metrics getMetrics() {
        return metrics;
    }
}
