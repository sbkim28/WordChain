package kr.ac.snu.sbkim28.game.dp;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsolePlayerInputReceiver implements PlayerInputReceiver{

    /**
     * 콘솔 입력을 받는 Reader 클래스.
     */
    private BufferedReader br;

    /**
     * {@link #waitUntilPlayerInput()} 에서 사용자 입력을 받기 위해서 대기하는데 사용되는 boolean
     * 입력을 받기 위해서 대기중일 때 true 값을 가지게 되며,
     * 대기 중에 해당 값이 false로 수정되면 대기를 중단하고 null을 반환하게 됨
     * @see #waitUntilPlayerInput()
     */
    private volatile boolean waitForUserInput;

    public ConsolePlayerInputReceiver(BufferedReader br) {
        this.br = br;
    }

    /**
     * 사용자로부터 콘솔을 통해서 입력을 받고 이를 반환함.
     * 메써드가 호출되었을 때 사용자로부터 입력을 받기까지 대기하게 됨.
     * {@link #haltWaiting()} method가 호출되면
     * 입력 대기를 중단하고 null을 반환함.
     * @return 사용자로부터 입력받은 word.
     * 사용자 입력 대기를 받는 도중 {@link #haltWaiting()} 가 호출되면
     * 대기를 멈추고 null을 반환함.
     */
    @Override
    public String waitUntilPlayerInput() {
        String word = null;
        waitForUserInput = true;
        try {
            while (waitForUserInput){
                if(br.ready()){
                    word = br.readLine();
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return word;
    }

    /**
     * 콘솔 입력 대기를 중단시킨다.
     * {@link #waitUntilPlayerInput()}에서 대기를 중단하고
     * null을 반환시킨다.
     */
    @Override
    public void haltWaiting() {
        waitForUserInput = false;
    }
}
