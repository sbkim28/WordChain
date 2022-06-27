package kr.ac.snu.sbkim28.game.io;

import kr.ac.snu.sbkim28.game.core.*;

import java.io.*;


/**
 * 콘솔로부터 사용자 입력을 받는 클래스.
 * @author sbkim28
 * @version 1.0
 * @see Player
 */
public class ConsoleInputPlayer extends AbstractDirectPlayer {

    private BufferedReader br;
    private BufferedWriter bw;

    /**
     * Constructor
     * System.in을 기본 InputStream으로 사용함.
     */
    public ConsoleInputPlayer() {
        this(new BufferedReader(new InputStreamReader(System.in)),
                new BufferedWriter(new OutputStreamWriter(System.out)));
    }

    public ConsoleInputPlayer(BufferedReader br, BufferedWriter bw) {
        this.br = br;
        this.bw = bw;
    }

    @Override
    public void notifyTurn(GameTurnState state) {
        super.notifyTurn(state);
    }

    @Override
    public void initialize(GameInitState state) {
        super.initialize(state);
    }

    @Override
    public void notifySuccess(WordResultState state) {
        super.notifySuccess(state);
    }

    @Override
    public String getWord() {
        return super.getWord();
    }

    @Override
    public void notifyGameOver(GameOverState state) {
        super.notifyGameOver(state);
    }

    @Override
    protected void createInputReceiver() {
        if(inputReceiver == null)
            inputReceiver = new ConsolePlayerInputReceiver(br);
    }

    @Override
    protected void createDisplayer() {
        if(displayer == null)
            displayer = new ConsoleGameDisplayer(bw);
    }


}
