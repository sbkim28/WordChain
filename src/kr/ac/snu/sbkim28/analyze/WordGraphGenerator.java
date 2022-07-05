package kr.ac.snu.sbkim28.analyze;

import kr.ac.snu.sbkim28.util.KoreanUtils;

import java.util.Collection;

/**
 * 단어 그래프 생성하는 클래스.
 * @author sbkim28
 * @version 1.0
 */
public class WordGraphGenerator {

    private WordMatrix matrix;
    private int len;

    /**
     * matrix에 대응되는 indexer.
     * @see Indexer
     */
    private Indexer indexer;

    public WordGraphGenerator() {
        indexer = new KoreanWordIndexer();
    }

    public void generate(Collection<String> data){
        for (String word : data){
            indexer.addChar(word.charAt(0));
            indexer.addChar(word.charAt(word.length() - 1));
        }
        len = indexer.getLength();

        matrix = new WordMatrix(len);

        for (String word : data){
            matrix.add(indexer.getIndex(word.charAt(0)),
                    indexer.getIndex(word.charAt(word.length() - 1)));
        }
    }

    public WordMatrix getMatrix() {
        return matrix;
    }

    public Indexer getIndexer() {
        return indexer;
    }
}
