package kr.ac.snu.sbkim28.game.io;

import kr.ac.snu.sbkim28.game.core.*;

/**
 * 직접적으로 사용자에게 입력을 받고 display를 하는 Player 클래스
 * @author sbkim28
 * @version 1.0.1
 */
public abstract class AbstractDirectPlayer extends AbstractPlayer {

    /**
     * 사용자에게 입력을 받고 처리하는 객체.
     * {@link #createInputReceiver} method가 호출될 때 생성된다.
     * @see PlayerInputReceiver
     * @see #createInputReceiver()
     */
    protected PlayerInputReceiver inputReceiver;

    /**
     * 사용자에게 게임 진행 상황을 보여주는 객체.
     * {@link #createDisplayer()} method가 호출될 때 생성된다.
     * @see GameDisplayer
     * @see #createDisplayer()
     */
    protected GameDisplayer displayer;

    public AbstractDirectPlayer() {
        super();
    }

    /**
     * {@link #createInputReceiver()}와 {@link #createDisplayer()} methods를
     * 호출하여 {@link #inputReceiver}와 {@link #displayer} 객체를 생성한다.
     * 이후 게임 시작을 displayer을 통해서 알린다.
     * @param state 게임 시작 상태
     */
    @Override
    public void initialize(GameInitState state) {
        super.initialize(state);
        this.createDisplayer();
        this.createInputReceiver();
        displayer.start(state);
    }

    /**
     * 사용자로부터 입력받은 단어를 리턴한다.
     * {@link #inputReceiver}를 통해서 사용자로부터 단어를 가져온다.
     * @return {@link PlayerInputReceiver#waitUntilPlayerInput()}의 리턴값.
     * @see PlayerInputReceiver#waitUntilPlayerInput()
     */
    @Override
    public String getWord() {
        displayer.getInput();
        return inputReceiver.waitUntilPlayerInput();
    }

    /**
     * 사용자로부터 입력을 받기위해 대기하는 것을 중단한다.
     * {@link #displayer}을 통해서 게임이 종료되었음을 알린다.
     * @param state 게임 종료 상태
     */
    @Override
    public void notifyGameOver(GameOverState state) {
        super.notifyGameOver(state);
        displayer.over(state);
        inputReceiver.haltWaiting();
    }

    /**
     * 단어 입력 결과를 받아서 이를 {@link #displayer}을 통해서 출력한다.
     * @param state 단어 입력 상태
     */
    @Override
    public void notifySuccess(WordResultState state) {
        super.notifySuccess(state);
        displayer.result(state);
    }

    /**
     * 플레이어의 턴임을 알리는 method.
     * {@link #displayer}을 통해서 이를 출력한다.
     * @param state 현재 상태.
     */
    @Override
    public void notifyTurn(GameTurnState state) {
        super.notifyTurn(state);
        displayer.turn(state);
    }

    /**
     * {@link #inputReceiver} 객체를 생성한다.
     * 필요에 따라서 inputReceiver 객체가
     * null인 경우에만 새롭게 생성하도록 구현할 수 있다.
     */
    protected abstract void createInputReceiver();

    /**
     * {@link #displayer} 객체를 생성한다.
     * 필요에 따라서 displayer 객체가
     * null인 경우에만 새롭게 생성하도록 구현할 수 있다.
     */
    protected abstract void createDisplayer();

}
