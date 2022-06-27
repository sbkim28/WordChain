package kr.ac.snu.sbkim28.game.io;

import kr.ac.snu.sbkim28.game.core.GameInitState;
import kr.ac.snu.sbkim28.game.core.GameOverState;
import kr.ac.snu.sbkim28.game.core.GameTurnState;
import kr.ac.snu.sbkim28.game.core.WordResultState;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConsoleGameDisplayer implements GameDisplayer{
    public static final String MSG_START= "게임이 시작되었습니다.";
    public static final String MSG_GUIDE_PLAYER_NUMBER = "당신은 'Player[%d]' 입니다.";
    public static final String MSG_PLAYER_TURN = "당신의 차례입니다. 주어진 문자로 시작하는 단어를 입력하세요.";
    public static final String MSG_TURN_CHAR = "시작 글자: %c";
    public static final String MSG_TURN_SUB_CHAR = "(%c)";
    public static final String MSG_GET_INPUT = "단어를 입력하세요: ";
    public static final String MSG_RESULT_SUCCESS = "유효한 단어입니다.";
    public static final String MSG_RESULT_FAIL = "사전에 없는 단어입니다.";
    public static final String MSG_RESULT_DUP = "이미 사용된 단어입니다.";
    public static final String MSG_RESULT_OTHERS = "Player[%d]가 입력한 단어는 '%s'입니다. ";

    public static final String MSG_GAMEOVER_SELF_TIMEOVER = "시간이 초과되었습니다. 당신의 패배입니다.";
    public static final String MSG_GAMEOVER_OTHER_TIMEOVER = "상대방이 시간 초과로 패배하였습니다. 당신의 승리입니다.";
    private BufferedWriter bw;
    private String startMsg;
    private String startGuidePlayerNumberMsg;
    private String turnMsg;
    private String turnCharMsg;
    private String turnSubCharMsg;
    private String getWordMsg;
    private String resultSuccessMsg;
    private String resultFailMsg;
    private String resultDuplicatedMsg;
    private String resultOthers;
    private String gameoverSelfTimeOver;
    private String gameoverOtherTimeOver;
    private int playerNumber;
    public ConsoleGameDisplayer(BufferedWriter bw) {
        this.bw = bw;
        startMsg = MSG_START;
        startGuidePlayerNumberMsg = MSG_GUIDE_PLAYER_NUMBER;
        turnMsg = MSG_PLAYER_TURN;
        turnCharMsg = MSG_TURN_CHAR;
        turnSubCharMsg = MSG_TURN_SUB_CHAR;
        getWordMsg = MSG_GET_INPUT;
        resultDuplicatedMsg = MSG_RESULT_DUP;
        resultSuccessMsg = MSG_RESULT_SUCCESS;
        resultFailMsg = MSG_RESULT_FAIL;
        resultOthers = MSG_RESULT_OTHERS;
        gameoverSelfTimeOver = MSG_GAMEOVER_SELF_TIMEOVER;
        gameoverOtherTimeOver = MSG_GAMEOVER_OTHER_TIMEOVER;
    }

    public void setStartMsg(String startMsg) {
        this.startMsg = startMsg;
    }

    public void setStartGuidePlayerNumberMsg(String startGuidePlayerNumberMsg) {
        this.startGuidePlayerNumberMsg = startGuidePlayerNumberMsg;
    }

    public void setTurnMsg(String turnMsg) {
        this.turnMsg = turnMsg;
    }

    public void setTurnCharMsg(String turnCharMsg) {
        this.turnCharMsg = turnCharMsg;
    }

    public void setTurnSubCharMsg(String turnSubCharMsg) {
        this.turnSubCharMsg = turnSubCharMsg;
    }

    public void setGetWordMsg(String getWordMsg) {
        this.getWordMsg = getWordMsg;
    }

    public void setResultSuccessMsg(String resultSuccessMsg) {
        this.resultSuccessMsg = resultSuccessMsg;
    }

    public void setResultFailMsg(String resultFailMsg) {
        this.resultFailMsg = resultFailMsg;
    }

    public void setResultDuplicatedMsg(String resultDuplicatedMsg) {
        this.resultDuplicatedMsg = resultDuplicatedMsg;
    }

    public void setResultOthers(String resultOthers) {
        this.resultOthers = resultOthers;
    }

    public void setGameoverSelfTimeOver(String gameoverSelfTimeOver) {
        this.gameoverSelfTimeOver = gameoverSelfTimeOver;
    }

    public void setGameoverOtherTimeOver(String gameoverOtherTimeOver) {
        this.gameoverOtherTimeOver = gameoverOtherTimeOver;
    }

    @Override
    public void start(GameInitState initState) {
        this.playerNumber = initState.playerNumber;
        try {
            bw.write(startMsg);
            bw.newLine();
            bw.write(String.format(startGuidePlayerNumberMsg, initState.playerNumber));
            bw.newLine();
            bw.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void turn(GameTurnState turnState) {
        try{
            bw.write(turnMsg);
            bw.newLine();
            bw.write(String.format(turnCharMsg, turnState.c));
            if(turnState.c == turnState.sub)
                bw.write(String.format(turnSubCharMsg, turnState.sub));
            bw.newLine();
            bw.flush();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getInput() {
        try {
            bw.write(getWordMsg);
            bw.flush();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void result(WordResultState resultState) {
        try {
            if(resultState.playerNumber == this.playerNumber){
                if(resultState.success)
                    bw.write(resultSuccessMsg);
                else {
                    switch (resultState.cause) {
                        case DUPLICATED -> bw.write(resultDuplicatedMsg);
                        case NOT_INCLUDED -> bw.write(resultFailMsg);
                    }
                }
            } else {
                if(resultState.success)
                    bw.write(String.format(resultOthers, resultState.playerNumber, resultState.word));
            }
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void over(GameOverState overState) {
        boolean thisPlayer = overState.playerNumber == playerNumber;
        try {
            switch (overState.cause){
                case TIMEOVER -> bw.write(thisPlayer ? gameoverSelfTimeOver : gameoverOtherTimeOver);
            }
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
