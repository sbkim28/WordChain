package kr.ac.snu.sbkim28.analyze;

import kr.ac.snu.sbkim28.util.KoreanUtils;

import java.util.Collection;

public class GraphAnalyzer {
    private WordMatrix matrix;
    private int[] charType;
    private Indexer indexer;
    private int length;

    private GraphAnalyzer(WordMatrix matrix, Indexer indexer){
        this.matrix = matrix;
        this.indexer = indexer;
        length = indexer.getLength();
    }

    public static GraphAnalyzer createAnalyzer(Collection<String> words){
        WordGraphGenerator generator = new WordGraphGenerator();
        generator.generate(words);
        return new GraphAnalyzer(generator.getMatrix(), generator.getIndexer());
    }

    public boolean containsChar(char c){
        return indexer.getIndex(c) != -1;
    }

    public int getSubIndex(int index){
        return indexer.getIndex(
                KoreanUtils.getSubChar(indexer.getChar(index)));
    }
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
        do {
            isSomethingChanged = false;

            for (int i = 0; i<length; ++i){
                int maxPriority = Integer.MIN_VALUE;
                if(charType[i] == 0)
                    continue;
                int maxType = 1;
                int sub = getSubIndex(i);
                if(sub == -1)
                    sub = i;
                for (int j = 0; j<length; ++j){
                    if(matrix.get(i, j) > 0 || matrix.get(sub, j) > 0) {
                        int priority = getTypePriority(charType[j]);
                        if (priority > maxPriority) {
                            maxType = charType[j];
                            maxPriority = priority;
                        }
                    }
                }
                if(maxPriority != 0){
                    int next = maxType + 1;
                    isSomethingChanged
                            |= next != charType[i];
                    charType[i] = next;
                }
            }
        } while (isSomethingChanged);
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
            if(matrix.get(index, i) > 0)
                return true;
        }
        return false;
    }

    public int getCharType(char c){
        return charType[indexer.getIndex(c)];
    }
}
