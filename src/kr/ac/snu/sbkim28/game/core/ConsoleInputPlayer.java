package kr.ac.snu.sbkim28.game.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ConsoleInputPlayer extends AbstractPlayer{

    private BufferedReader br;
    private volatile boolean waitForUserInput;

    public ConsoleInputPlayer() {
        this(new BufferedReader(new InputStreamReader(System.in)));
    }
    public ConsoleInputPlayer(BufferedReader reader){
        super();
        br = reader;
    }

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
