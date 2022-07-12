package kr.ac.snu.sbkim28.analyze;

import kr.ac.snu.sbkim28.analyze.graph.IndexedGraph;
import kr.ac.snu.sbkim28.analyze.graph.Indexer;
import kr.ac.snu.sbkim28.util.KoreanUtils;

import java.util.Collection;

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
        for (int i = 0; i < length; ++i) {
            if(charType[i] != 0)
                ret |= setCharTypeByPriority(i);
        }
        return ret;
    }

    private boolean setCharTypeByPriority(int i){
        int sub = getSubIndex(i);

        int max = Integer.MIN_VALUE;
        int maxType = 1;

        for (int j = 0; j < length; ++j) {
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
    public int getMaxChoices(char c, Collection<Character> choices){
        int prev = getPrevType(c);
        int len = 0;
        int index = indexer.getIndex(c);
        int sub = getSubIndex(index);
        for (int i = 0; i < length; ++i) {
            if(graph.containsEdge(index, i) || graph.containsEdge(sub, i)){
                if(charType[i] == prev && choices.add(indexer.getChar(i))){
                    ++len;
                }
            }
        }
        return len;
    }
    private int getSubIndex(int index){
        int sub = indexer.getIndex(KoreanUtils.getSubChar(indexer.getChar(index)));
        if(sub == -1)
            sub = index;
        return sub;
    }

    @Override
    public int findCharWithType(int type, Collection<Character> chars) {
        int len = 0;
        for (int i = 0; i < length; ++i) {
            if(charType[i] == type && chars.add(indexer.getChar(i))){
                ++len;
            }
        }
        return len;
    }
}
