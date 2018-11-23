package com.ignited.wordchain.play;

import com.ignited.wordchain.play.user.ai.RewardGettable;
import com.ignited.wordchain.play.env.ActionState;
import com.ignited.wordchain.play.env.Environment;
import com.ignited.wordchain.play.env.GameEnvironment;
import com.ignited.wordchain.play.print.GamePrintable;
import com.ignited.wordchain.play.user.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private final List<Player> players;
    private GamePrintable printable;

    private GameEnvironment gameEnvironment;

    private int turn;
    private char[] chainKey;

    public GameManager(GameEnvironment gameEnvironment) {
        this(gameEnvironment, new ArrayList<>());
    }

    public GameManager(GameEnvironment gameEnvironment, List<Player> players) {
        this(gameEnvironment, players, new GamePrintable() {
            @Override public void failMsg(ActionState ft) { }
            @Override public void finishMsg(String winner) { }
            @Override public void playerStateMsg(String player, char... chainKey) { }
            @Override public void playerSubmit(String sub) { }
        });
    }

    public GameManager(GameEnvironment gameEnvironment, List<Player> players, GamePrintable printable) {
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
        turn = 0;
        boolean flag = true;
        while (flag){
            flag = playGames();
        }
        gameEnvironment.reset();
        chainKey = new char[]{0};
    }

    private boolean playGames(){
        int i = getTurn();
        while (true) {

            Player p = players.get(i);
            printable.playerStateMsg(p.getName(), chainKey);
            String str = p.submitWord();

            if(p.isPrintSubmit()) printable.playerSubmit(str);

            ActionState state = gameEnvironment.validateAction(str);
            if(state != ActionState.SUCCESS){
                printable.failMsg(state);
                continue;
            }

            Environment.Result result = gameEnvironment.step(str);

            chainKey = result.getState();
            if(turn > 0){
                Player prev = players.get((turn - 1) % players.size());
                if(prev instanceof RewardGettable){
                    ((RewardGettable) prev).reward(result.getReward());
                }
            }
            ++turn;
            return !result.isDone();
        }
    }

    protected int getTurn(){
        return turn % players.size();
    }
}
