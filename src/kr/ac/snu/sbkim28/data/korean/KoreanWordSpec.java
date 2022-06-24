package kr.ac.snu.sbkim28.data.korean;

public class KoreanWordSpec {
    public final String wordClass;
    public final String[] attributes;
    public final String[] meanings;

    KoreanWordSpec(String wordClass, String[] attributes, String[] meanings) {
        this.wordClass = wordClass;
        this.attributes = attributes;
        this.meanings = meanings;
    }


}
