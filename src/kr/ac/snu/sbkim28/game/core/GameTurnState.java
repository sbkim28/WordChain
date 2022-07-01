package kr.ac.snu.sbkim28.game.core;

import java.io.Serializable;

public class GameTurnState implements Serializable {
    public final char c;
    public final char sub;

    public GameTurnState(char c) {
        this(c, '\0');
    }

    public GameTurnState(char c, char sub) {
        this.c = c;
        this.sub = sub;
    }

}
