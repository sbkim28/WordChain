package kr.ac.snu.sbkim28.game.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * 콘솔로부터 사용자 입력을 받는 클래스.
 * @author sbkim28
 * @version 1.0
 * @see Player
 */
public class ConsoleInputPlayer extends AbstractPlayer{

    /**
     * 콘솔 입력을 받는 Reader 클래스.
     */
    private BufferedReader br;
    /**
     * {@link #getWord()}에서 사용자 입력을 받기 위해서 대기하는데 사용되는 boolean
     * 입력을 받기 위해서 대기중일 때 true 값을 가지게 되며,
     * 대기 중에 해당 값이 false로 수정되면 대기를 중단하고 null을 반환하게 됨
     * @see ConsoleInputPlayer#getWord()
     */
    private volatile boolean waitForUserInput;

    /**
     * Constructor
     * System.in을 기본 InputStream으로 사용함.
     */
    public ConsoleInputPlayer() {
        this(new BufferedReader(new InputStreamReader(System.in)));
    }
    public ConsoleInputPlayer(BufferedReader reader){
        super();
        br = reader;
    }

    /**
     * 사용자로부터 콘솔을 통해서 입력을 받고 이를 반환함.
     * 메써드가 호출되었을 때 사용자로부터 입력을 받기까지 대기하게 됨.
     * {@link Player#notifyGameOver()} method가 호출되면
     * 입력 대기를 중단하고 null을 반환함.
     * @return 사용자로부터 입력받은 word.
     * 사용자 입력 대기를 받는 도중 {@link Player#notifyGameOver()}가 호출되면
     * 대기를 멈추고 null을 반환함.
     */
    @Override
    public String getWord() {
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

    @Override
    public void notifySuccess(boolean success) {

    }

    @Override
    public void notifyGameOver() {
        waitForUserInput = false;
    }
}
