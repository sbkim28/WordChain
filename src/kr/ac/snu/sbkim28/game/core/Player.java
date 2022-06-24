package kr.ac.snu.sbkim28.game.core;

public interface Player {

    void notifyTurn(char c);
    void notifyTurn(char c, char sub);
    String getWord();
    void notifySuccessed(boolean success);
    void notifyGameOver();

}
