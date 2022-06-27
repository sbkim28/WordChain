package kr.ac.snu.sbkim28.game.core;

public abstract class AbstractPlayer implements Player {

    private GameTurnState turnState;
    private WordResultState resultState;

    private GameInitState initState;

    public AbstractPlayer() {
    }

    public GameTurnState getTurnState() {
        return turnState;
    }

    public WordResultState getResultState() {
        return resultState;
    }

    public GameInitState getInitState() {
        return initState;
    }

    @Override
    public void notifySuccess(WordResultState state) {
        this.resultState = state;
    }

    @Override
    public void initialize(GameInitState state) {
        this.initState = state;
    }

    @Override
    public void notifyTurn(GameTurnState state) {
        this.turnState = state;
    }
}
