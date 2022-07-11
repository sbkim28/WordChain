package kr.ac.snu.sbkim28.analyze;

import kr.ac.snu.sbkim28.analyze.graph.IndexedGraph;
import kr.ac.snu.sbkim28.analyze.graph.Indexer;
import kr.ac.snu.sbkim28.util.KoreanUtils;

import java.util.ArrayList;
import java.util.List;

public class IndexedCharTypeFinder extends CharTypeFinder {

    private IndexedGraph graph;
    private Indexer indexer;
    private int length;
    private int[] charType;

    public IndexedCharTypeFinder(IndexedGraph graph) {
        super(graph);
        this.graph = graph;
        indexer = graph.getIndexer();
    }

    @Override
    public void initializeCharType() {
        length = indexer.getLength();
        charType = new int[length];
        findCharWithoutEdge();
        boolean isSomethingChanged;
        do {
            isSomethingChanged = setCharTypeByPriority();
        } while (isSomethingChanged);
    }

    @Override
    public int getCharType(char c) {
        return charType[indexer.getIndex(c)];
    }

    private void findCharWithoutEdge(){
        for (int i = 0; i < length; ++i) {
            char c = indexer.getChar(i);
            charType[i] = hasOutEdge(c) ? -1 : 0;
        }
    }

    private boolean setCharTypeByPriority(){
        boolean ret = false;
        for (int i = 0; i < length; i++) {
            if(charType[i] != 0)
                ret |= setCharTypeByPriority(i);
        }
        return ret;
    }

    private boolean setCharTypeByPriority(int i){
        int sub = indexer.getIndex(KoreanUtils.getSubChar(indexer.getChar(i)));
        if(sub == -1)
            sub = i;

        int max = Integer.MIN_VALUE;
        int maxType = 1;

        for (int j = 0; j < length; j++) {
            if(graph.containsEdge(i, j) || graph.containsEdge(sub, j)){
                int priority = CharTypeFinder.getTypePriority(charType[j]);
                if(priority > max){
                    max = priority;
                    maxType = charType[j];
                }
            }
        }

        boolean ret = false;
        if(max != 0){
            int next = maxType + 1;
            ret = next != charType[i];
            charType[i] = next;
        }
        return ret;
    }

    @Override
    public List<Character> getMaxChoices(char c){
        int prev = getCharType(c);
        if(prev > 0)
            --prev;

        List<Character> ret = new ArrayList<>(prev > 0 ? 1 << 4 : 1 << 5);
        int index = indexer.getIndex(c);
        int sub = indexer.getIndex(KoreanUtils.getSubChar(c));
        if(sub == -1)
            sub = index;
        for (int i = 0; i < length; i++) {
            if(graph.containsEdge(index, i) || graph.containsEdge(sub, i)){
                if(charType[i] == prev)
                    ret.add(indexer.getChar(i));
            }
        }
        return ret;
    }

    @Override
    public List<Character> findCharWithType(int type) {
        List<Character> ret = new ArrayList<>(1 << 8);

        return ret;
    }

}
