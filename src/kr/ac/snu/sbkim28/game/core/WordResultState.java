package kr.ac.snu.sbkim28.game.core;

import java.io.Serializable;

public class WordResultState implements Serializable {
    public final boolean success;
    public final Cause cause;
    public final int playerNumber;
    public final String word;

    public WordResultState(boolean success, int playerNumber) {
        this.success = success;
        this.playerNumber = playerNumber;
        this.word = null;
        this.cause = Cause.SUCCESS;
    }

    public WordResultState(boolean success, Cause cause, int playerNumber, String word) {
        this.success = success;
        this.cause = cause;
        this.playerNumber = playerNumber;
        this.word = word;
    }

    public enum Cause {
        SUCCESS, DUPLICATED, NOT_INCLUDED;
    }
}
