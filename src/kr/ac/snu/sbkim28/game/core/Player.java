package kr.ac.snu.sbkim28.game.core;

/**
 * 끝말잇기에서 사용자의 입력을 처리함.
 * @author sbkim28
 * @version 1.0
 * @see kr.ac.snu.sbkim28.game.dp.DirectPlayer
 * @see LocalEnvironment
 */
public interface Player {

    /**
     * 처음 게임이 시작되었을 때 호출됨.
     */
    void initialize(GameInitState state);

    /**
     * 플레이어에게 자신의 턴이 왔음을 알려줌.
     * {@link LocalEnvironment}에서 해당 플레이어의 턴이 올 때 호출됨.
     * @param state 현재 상태.
     */
    void notifyTurn(GameTurnState state);

    /**
     * 사용자로부터 입력을 받고 이를 반환함.
     * 메써드가 호출되었을 때 사용자로부터 입력을 받기까지 대기하게 됨.
     * {@link Player#notifyGameOver()} method가 호출되면
     * 입력 대기를 중단하고 null을 반환함.
     * @return 사용자로부터 입력받은 word.
     * 사용자 입력 대기를 받는 도중 {@link Player#notifyGameOver()}가 호출되면
     * 대기를 멈추고 null을 반환함.
     */
    String getWord();

    /**
     * 사용자가 입력한 단어가 성공하였는지 여부를 알려줌.
     * {@link LocalEnvironment}에서 {@link Player#getWord()}를 호출한 이후
     * 결과를 이 method를 통해서 전달함.
     * {@link Player#notifySuccess(WordResultState)} 메써드를 사용하는 것을 권장.
     * 이후 버전에서 삭제 예정.
     */
    @Deprecated
    default void notifySuccess(boolean success){
        notifySuccess(new WordResultState(success, 0));
    }

    void notifySuccess(WordResultState state);

    /**
     * 게임이 종료되었는지 호출함.
     * {@link LocalEnvironment}에서 시간 초과 등의 이유로 게임이 끝났을 경우
     * 이 메써드가 호출됨.
     * 이 메써드가 호출되면 {@link #getWord()} 대기를 중단하여야 하며,
     * {@link #getWord()}에서는 null을 반환하여야 함.
     * {@link Player#notifyGameOver(GameOverState)} 메써드를 사용하는 것을 권장.
     * 이후 버전에서 삭제 예정.
     */
    @Deprecated
    default void notifyGameOver(){
        notifyGameOver(new GameOverState(0));
    }

    void notifyGameOver(GameOverState state);

}
