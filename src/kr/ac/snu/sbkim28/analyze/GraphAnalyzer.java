package kr.ac.snu.sbkim28.analyze;

import kr.ac.snu.sbkim28.util.KoreanUtils;

import java.util.Collection;
import static kr.ac.snu.sbkim28.util.KoreanUtils.getSubChar;

public class GraphAnalyzer {
    private int[][] matrix;

    private int[] charType;
    private Indexer indexer;
    private int length;

    public GraphAnalyzer(Collection<String> wordSet) {
        WordGraphGenerator wgg = new WordGraphGenerator();
        wgg.generate(wordSet);
        matrix = wgg.getMatrix();
        indexer = wgg.getIndexer();
        length = indexer.getLength();
    }

    public int getSubIndex(int index){
        return indexer.getIndex(
                KoreanUtils.getSubChar(indexer.getChar(index)));
    }

    // WARNING: There is bug related to sub char...
    public void findCharType(){
        charType = new int[length];

        for (int i = 0; i<length; ++i){
            int sub;
            charType[i] = !hasOutEdge(i)
                    &&
                    ((sub = getSubIndex(i)) == i || sub == -1 ||
                    !hasOutEdge(sub)) ? 0 : -1;
        }

        boolean isSomethingChanged;
        do{
            isSomethingChanged = false;

            for (int i = 0; i<length; ++i){
                int maxPriority = Integer.MIN_VALUE;
                if(charType[i] == 0)
                    continue;
                int maxType = -1;
                int sub = getSubIndex(i);
                if(sub == -1)
                    sub = i;
                for (int j = 0; j<length; ++j){
                    if(i == j) // to check self loop;
                        continue;
                    if(matrix[i][j] != 0 || matrix[sub][j] != 0) {
                        int priority = getTypePriority(charType[j]);
                        if (priority > maxPriority) {
                            maxType = charType[j];
                            maxPriority = priority;
                        }
                    }
                }

                if(maxPriority > 0){
                    int next = maxType + 1;
                    isSomethingChanged
                            |= next != charType[i];
                    charType[i] = next;
                } else if(maxPriority < 0){
                    int next = maxType + 1 + matrix[i][i];
                    if(sub != i)
                        next += matrix[sub][i];
                    isSomethingChanged
                            |= next != charType[i];
                    charType[i] = next;
                }
            }
        } while (isSomethingChanged);

        int i = 0;
    }

    public static int getTypePriority(int i){
        if(i < 0)
            i = 0; // neutral word for 0
        else if((i & 1) == 0){
            i = Integer.MAX_VALUE & ~i; // n State
        } else {
            i |= Integer.MIN_VALUE; // p State
        }
        return i;
    }

    public boolean hasOutEdge(int index){
        for (int i = 0; i<length; ++i){
            if(matrix[index][i] != 0)
                return true;
        }
        return false;
    }
}
