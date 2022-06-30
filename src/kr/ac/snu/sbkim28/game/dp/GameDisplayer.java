package kr.ac.snu.sbkim28.game.dp;

import kr.ac.snu.sbkim28.game.core.GameInitState;
import kr.ac.snu.sbkim28.game.core.GameOverState;
import kr.ac.snu.sbkim28.game.core.GameTurnState;
import kr.ac.snu.sbkim28.game.core.WordResultState;

/**
 * 게임 진행 상황을 유저에게 알려주는 클래스.
 * @author sbkim28
 * @version 1.0.1
 */
public interface GameDisplayer {
    void start(GameInitState initState);
    void turn(GameTurnState turnState);
    void getInput();
    void result(WordResultState resultState);
    void over(GameOverState overState);
}
