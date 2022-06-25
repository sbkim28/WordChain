package kr.ac.snu.sbkim28.data;

import java.util.Set;
import java.util.function.Consumer;

/**
 * 단어를 선별함.
 */
public interface IWordSelector {

    /**
     * 모든 단어 목록을 읽고 가져옴.
     */
    Set<String> getWords();

    /**
     * 단어 목록으로부터 순차적으로 단어를 가져옴.
     * 단어 목록중에서 {@link Filterable#filter(IWord)}을
     * 통과하는 단어가 나올 때까지 단어를 찾고, 해당하는 단어를 발견한다면
     * 이를 반환함.
     *
     * @return 필터링 기준을 만족하는 단어를 String으로 반환함.
     * 만약 이를 만족하는 다음의 단어가 존재하지 않을 경우
     * null을 반환함.
     * @see Filterable
     */
    String filter();

}
