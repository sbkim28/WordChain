package kr.ac.snu.sbkim28.game.core;

public class GameState {
    public final char c;
    public final char sub;

    public GameState(char c) {
        this(c, '\0');
    }

    public GameState(char c, char sub) {
        this.c = c;
        this.sub = sub;
    }
}
