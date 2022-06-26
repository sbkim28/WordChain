package kr.ac.snu.sbkim28.game.core;

public abstract class AbstractPlayer implements Player {

    private GameState state;

    public AbstractPlayer() {
    }

    public GameState getState() {
        return state;
    }

    @Override
    public void notifyTurn(GameState state) {
        this.state = state;
    }
}
