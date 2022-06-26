package kr.ac.snu.sbkim28.analyze;

import java.util.Collection;
import static kr.ac.snu.sbkim28.util.KoreanUtils.getSubChar;

public class GraphAnalyzer {
    private int[][] matrix;
    private boolean[] absoluteNPosition;
    private boolean[] absolutePPosition;
    private Indexer indexer;
    private int length;

    public GraphAnalyzer(Collection<String> wordSet) {
        WordGraphGenerator wgg = new WordGraphGenerator();
        wgg.generate(wordSet);
        matrix = wgg.getMatrix();
        indexer = wgg.getIndexer();
    }

    public void createAbsoluteNPosition(){
        absoluteNPosition = new boolean[length];
        for (int i = 0; i<length; ++i){
            if(!hasOutEdge(i)){
                int sub = indexer.getIndex(getSubChar(indexer.getChar(i)));
                if(sub != -1 && !hasOutEdge(sub)){
                    absoluteNPosition[i] = true;
                }
            }
        }
    }

    public void createAbsolutePPosition(){
        absolutePPosition = new boolean[length];
        for (int i = 0; i<length; ++i){
            for (int j = 0; j<length; ++j){
                //todo
            }
        }
    }

    public boolean hasOutEdge(int index){
        for (int i = 0; i<length; ++i){
            if(matrix[index][i] != 0)
                return true;
        }
        return false;
    }

}
