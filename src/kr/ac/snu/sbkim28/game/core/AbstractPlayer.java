package kr.ac.snu.sbkim28.game.core;

public abstract class AbstractPlayer implements Player {

    private char currentChar;
    private char currentSubChar;

    public AbstractPlayer() {
    }

    public char getCurrentChar() {
        return currentChar;
    }

    public char getCurrentSubChar() {
        return currentSubChar;
    }

    @Override
    public void notifyTurn(char c) {
        notifyTurn(c, '\0');
    }

    @Override
    public void notifyTurn(char c, char sub) {
        currentChar = c;
        currentSubChar = sub;
    }


}
