package kr.ac.snu.sbkim28.game.core;

import java.util.Set;

public interface Environment {
    void setWordSet(Set<String> wordSet);
    void runGame();
}
