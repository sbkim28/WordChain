package kr.ac.snu.sbkim28.data.korean;

import kr.ac.snu.sbkim28.data.IWord;

public class KoreanWord implements IWord, Comparable<KoreanWord>{
    public final int homonym;
    public final String word;
    public KoreanWordSpec[] spec;

    public KoreanWord(int homonym, String word) {
        this.homonym = homonym;
        this.word = word;
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public int compareTo(KoreanWord o) {
        int ret = word.compareTo(o.word);
        if(ret == 0){
            ret = Integer.compare(homonym, o.homonym);
        }
        return ret;
    }


}
