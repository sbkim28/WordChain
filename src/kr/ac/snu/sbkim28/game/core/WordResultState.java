package kr.ac.snu.sbkim28.game.core;

import java.io.Serializable;

public class WordResultState implements Serializable {
    public final boolean success;
    public final int playerNumber;
    public final String word;

    public WordResultState(boolean success, int playerNumber) {
        this.success = success;
        this.playerNumber = playerNumber;
        this.word = null;
    }
}
