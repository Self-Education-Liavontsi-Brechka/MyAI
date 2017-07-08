package search.adversarial;

import common.action.Action;
import environment.state.EnvironmentState;

import java.util.List;

public interface Game {
    EnvironmentState getInitialState();

    Player[] getPlayers();

    Player getPlayer(EnvironmentState state);

    List<Action> getActions(EnvironmentState state);

    EnvironmentState getResult(EnvironmentState state, Action action);

    boolean isTerminal(EnvironmentState state);

    double getUtility(EnvironmentState state, Player player);
}
