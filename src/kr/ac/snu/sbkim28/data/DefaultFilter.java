package kr.ac.snu.sbkim28.data;

/**
 * 기본 필터. 모든 단어를 허용함.
 * @author sbkim28
 * @version 1.0
 * @see Filterable
 */
public class DefaultFilter<T extends  IWord> implements Filterable<T>{
    /**
     * 항상 참을 반환함.
     */
    @Override
    public boolean filter(T word) {
        return true;
    }
}
