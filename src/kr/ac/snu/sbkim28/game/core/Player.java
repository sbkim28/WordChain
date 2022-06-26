package kr.ac.snu.sbkim28.game.core;

/**
 * 끝말잇기에서 사용자의 입력을 처리함.
 * @author sbkim28
 * @version 1.0
 * @see ConsoleInputPlayer
 * @see LocalEnvironment
 */
public interface Player {

    /**
     * 플레이어에게 자신의 턴이 왔음을 알려줌.
     * {@link LocalEnvironment}에서 해당 플레이어의 턴이 올 때 호출됨.
     * @param c 현재 글자.
     */
    void notifyTurn(char c);

    /**
     * 플레이어에게 자신의 턴이 왔음을 알려줌.
     * {@link LocalEnvironment}에서 해당 플레이어의 턴이 올 때 호출됨.
     * @param c 현재 글자.
     * @param sub 두음법칙을 적용할 수 있는 경우의 글자.
     */
    void notifyTurn(char c, char sub);

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
     */
    void notifySuccess(boolean success);

    /**
     * 게임이 종료되었는지 호출함.
     * {@link LocalEnvironment}에서 시간 초과 등의 이유로 게임이 끝났을 경우
     * 이 메써드가 호출됨.
     * 이 메써드가 호출되면 {@link #getWord()} 대기를 중단하여야 하며,
     * {@link #getWord()}에서는 null을 반환하여야 함.
     */
    void notifyGameOver();

}
