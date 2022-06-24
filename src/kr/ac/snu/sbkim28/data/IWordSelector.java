package kr.ac.snu.sbkim28.data;

import java.util.Set;
import java.util.function.Consumer;

public interface IWordSelector {
    Set<String> getWords();
    String filter();

}
