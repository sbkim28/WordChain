package kr.ac.snu.sbkim28.data;

/**
 * 단어 필터링.
 * @param <T> 필터링할 단어의 타입.
 * @see kr.ac.snu.sbkim28.data.korean.KoreanFilter
 */
public interface Filterable<T extends IWord> {
    /**
     * 단어를 필터링함. {@link kr.ac.snu.sbkim28.data.korean.KoreanWordSelector}에서
     * 모든 단어에 대해서 이 메써드를 호출하고, return값이 참인 단어만을 선별함.
     * @return 주어진 단어가 적절하다면 true, 그렇지 않다면 false.
     * @see kr.ac.snu.sbkim28.data.korean.KoreanWordSelector
     */
    boolean filter(T word);
}
