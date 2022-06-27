package kr.ac.snu.sbkim28.game.io;

/**
 * 플레이어로부터 입력을 받음.
 * 플레이어로부터 입력을 받는 메써드와 입력 대기를 중단하는 메써드를 가지고 있음.
 *
 * @author sbkim28
 * @version 1.0.1
 */
public interface PlayerInputReceiver {
    /**
     * 사용자로부터 입력을 받고 이를 반환함.
     * 메써드가 호출되었을 때 사용자로부터 입력을 받기까지 대기함.
     * {@link #haltWaiting()} method가 호출되면
     * 입력 대기를 중단하고 null을 반환함.
     * @return 사용자로부터 입력받은 word.
     * 사용자 입력 대기를 받는 도중 {@link #haltWaiting()} 가 호출되면
     * 대기를 멈추고 null을 반환함.
     */
    String waitUntilPlayerInput();

    /**
     * {@link #waitUntilPlayerInput()} 메써드의 입력 대기를 중지하고 null을 반환하게 함.
     */
    void haltWaiting();
}
