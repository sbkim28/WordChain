package kr.ac.snu.sbkim28.game.core;

import java.util.Set;

public interface Environment {
    default void setWordSet(Set<String> wordSet){}

    /**
     * 서버에서 초기 상태를 설정함.
     */
    void initialize();
    void runGame();
}
