package com.ignited.wordchain.play;

import com.ignited.wordchain.play.env.ActionState;
import com.ignited.wordchain.play.env.Environment;
import com.ignited.wordchain.play.print.GamePrintable;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private final List<Player> players;
    private GamePrintable printable;

    private Environment gameEnvironment;

    private int turn;
    private char[] chainKey;

    public GameManager(Environment gameEnvironment) {
        this(gameEnvironment, new ArrayList<>());
    }

    public GameManager(Environment gameEnvironment, List<Player> players) {
        this(gameEnvironment, players, new GamePrintable() {
            @Override public void failMsg(ActionState ft) { }
            @Override public void finishMsg(String winner) { }
            @Override public void playerStateMsg(String player, char... chainKey) { }
            @Override public void playerSubmit(String sub) { }
        });
    }

    public GameManager(Environment gameEnvironment, List<Player> players, GamePrintable printable) {
        this.gameEnvironment = gameEnvironment;
        this.players = players;
        this.printable = printable;
        chainKey = new char[]{0};
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPrintable(GamePrintable printable) {
        this.printable = printable;
    }

    public void play(){
        boolean flag = true;
        while (flag){
            flag = playGames();
        }
    }

    private boolean playGames(){
        int i = getTurn();
        while (true) {

            Player p = players.get(i);
            printable.playerStateMsg(p.getName(), chainKey);
            String str = p.submitWord(chainKey);

            if(p.isPrintSubmit()) printable.playerSubmit(str);

            ActionState state = gameEnvironment.validateAction(str);
            if(state != ActionState.SUCCESS){
                printable.failMsg(state);
                continue;
            }

            Environment.Result result = gameEnvironment.step(str);

            chainKey = result.getObservation();

            return !result.isDone();
        }
    }

    private int getTurn(){
        return turn++ % players.size();
    }
}
