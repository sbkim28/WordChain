package kr.ac.snu.sbkim28.game.core;

import java.io.Serializable;

public class GameOverState implements Serializable {
    public final int playerNumber;
    public Cause cause;

    public GameOverState(int playerNumber) {
        this.playerNumber = playerNumber;
        this.cause = Cause.TIMEOVER;
    }

    public enum Cause {
        TIMEOVER;
    }
}
