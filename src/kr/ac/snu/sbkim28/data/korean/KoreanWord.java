package kr.ac.snu.sbkim28.data.korean;

import kr.ac.snu.sbkim28.data.IWord;


/**
 * 한국어 단어 클래스
 * @author sbkim28
 * @version 1.0
 */
public class KoreanWord implements IWord, Comparable<KoreanWord>{

    /**
     * 동음의의어 번호. {@link KoreanWord#word}가 같은 단어를
     * 구별하기 위해서 사용함.
     */
    public final int homonym;
    /**
     * 한국어 단어.
     */
    public final String word;

    /**
     * 단어의 세부 정보. {@link KoreanWordSpec} 참고
     */
    public KoreanWordSpec[] spec;

    public KoreanWord(int homonym, String word) {
        this.homonym = homonym;
        this.word = word;
    }

    @Override
    public String toString() {
        return word;
    }

    /**
     * 단어 비교. {@link KoreanWord#word}를 우선 비교하고 만약 같으면
     * {@link KoreanWord#homonym}을 비교한 결과를 리턴함.
     */
    @Override
    public int compareTo(KoreanWord o) {
        int ret = word.compareTo(o.word);
        if(ret == 0){
            ret = Integer.compare(homonym, o.homonym);
        }
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof KoreanWord && compareTo((KoreanWord) obj) == 0;
    }
}
