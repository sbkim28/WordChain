package kr.ac.snu.sbkim28.analyze;

import kr.ac.snu.sbkim28.util.KoreanUtils;

import java.util.Collection;

/**
 * 단어 그래프 생성하는 클래스.
 * @author sbkim28
 * @version 1.0
 */
public class WordGraphGenerator {

    /**
     * 생성할 인접행렬
     */
    private int[][] matrix;
    private int len;

    /**
     * matrix에 대응되는 indexer.
     * @see Indexer
     */
    private Indexer indexer;

    public WordGraphGenerator() {}

    public void generate(Collection<String> data){
        for (String word : data){
            indexer.addChar(word.charAt(0));
            indexer.addChar(word.charAt(word.length() - 1));
        }
        len = indexer.getLength();

        matrix = new int[len][len];

        for (String word : data){
            ++matrix[indexer.getIndex(word.charAt(0))][indexer.getIndex(word.charAt(word.length() - 1))];
        }
    }

    public int[][] getMatrix() {
        int[][] ret = new int[len][len];
        for (int i = 0; i<len;++i){
            System.arraycopy(matrix[i], 0, ret[i], 0, len);
        }
        return ret;
    }

    public Indexer getIndexer() {
        return indexer;
    }

    public int getLen() {
        return len;
    }

    public int convertKorean2int(char c){
        return KoreanUtils.convert2int(c);
    }

    public char convertint2Korean(int i){
        return KoreanUtils.convert2char(i);
    }

}
