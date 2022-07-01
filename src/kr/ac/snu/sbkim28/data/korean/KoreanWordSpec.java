package kr.ac.snu.sbkim28.data.korean;

/**
 * 한국어 단어 세부 사항 클래스
 * 표준국어대사전의 단어 서술 체계에 맞도록 구성됨.
 * @author sbkim28
 * @version 1.0
 */
public class KoreanWordSpec {

    /**
     * 단어의 품사
     * ex) 명사, 대명사, 부사 등등
     */
    public final String wordClass;
    /**
     * 단어의 속성
     * ex) 인명, 고적, 옛말, 방언 등등
     */
    public final String[] attributes;
    /**
     * 단어의 뜻
     */
    public final String[] meanings;

    KoreanWordSpec(String wordClass, String[] attributes, String[] meanings) {
        this.wordClass = wordClass;
        this.attributes = attributes;
        this.meanings = meanings;
    }
}
