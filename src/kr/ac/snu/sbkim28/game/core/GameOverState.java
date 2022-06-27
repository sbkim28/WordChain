package kr.ac.snu.sbkim28.game.core;

import java.io.Serializable;

public class GameOverState implements Serializable {
    public final int playerNumber;

    public GameOverState(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
