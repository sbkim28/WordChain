package kr.ac.snu.sbkim28.game.core;

public abstract class AbstractPlayer implements Player {

    private GameTurnState turnState;
    private WordResultState resultState;

    public AbstractPlayer() {
    }

    public GameTurnState getTurnState() {
        return turnState;
    }

    public WordResultState getResultState() {
        return resultState;
    }

    @Override
    public void notifySuccess(WordResultState state) {

    }

    @Override
    public void notifyTurn(GameTurnState state) {
        this.turnState = state;
    }
}
