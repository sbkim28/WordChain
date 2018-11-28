package com.ignited.wordchain.play;

import com.ignited.wordchain.play.user.ai.ResultGettable;
import com.ignited.wordchain.play.env.ActionState;
import com.ignited.wordchain.play.env.Environment;
import com.ignited.wordchain.play.env.GameEnvironment;
import com.ignited.wordchain.play.print.GamePrintable;
import com.ignited.wordchain.play.user.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private final Player[] players;

    private GamePrintable printable;

    private GameEnvironment gameEnvironment;

    private int turn;

    public GameManager(GameEnvironment gameEnvironment, Player player1, Player player2) {
        this(gameEnvironment, player1, player2, new GamePrintable() {
            @Override
            public void failMsg(ActionState as) {}
            @Override
            public void finishMsg(String winner) {}
            @Override
            public void playerStateMsg(String player, String chainKey) {}
            @Override
            public void playerSubmit(String sub) {}
        });
    }

    public GameManager(GameEnvironment gameEnvironment, Player player1, Player player2, GamePrintable printable) {
        this.gameEnvironment = gameEnvironment;
        this.players = new Player[]{player1, player2};
        this.printable = printable;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPrintable(GamePrintable printable) {
        this.printable = printable;
    }

    public void play(){
        turn = 0;
        boolean flag = true;
        while (flag){
            flag = playGames();
        }
        gameEnvironment.reset();
    }

    private boolean playGames(){
        int i = getTurn();
        while (true) {

            Player p = players[i];
            printable.playerStateMsg(p.getName(), gameEnvironment.getState());
            String str = p.submitWord();

            if(p.isPrintSubmit()) printable.playerSubmit(str);

            ActionState state = gameEnvironment.validateAction(str);
            if(state != ActionState.SUCCESS){
                printable.failMsg(state);
                continue;
            }

            Environment.Result result = gameEnvironment.step(str);

            Player prev = players[(turn + 1) % players.length];
            if(prev instanceof ResultGettable){
                ((ResultGettable) prev).get(result);
            }

            ++turn;
            return !result.isDone();
        }
    }

    protected int getTurn(){
        return turn % players.length;
    }
}
