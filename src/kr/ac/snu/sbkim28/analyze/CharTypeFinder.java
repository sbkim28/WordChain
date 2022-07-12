package kr.ac.snu.sbkim28.analyze;

import kr.ac.snu.sbkim28.analyze.graph.WordGraph;
import kr.ac.snu.sbkim28.util.KoreanUtils;

import java.util.Collection;

public abstract class CharTypeFinder {

    private WordGraph graph;

    public CharTypeFinder(WordGraph graph) {
        this.graph = graph;
    }

    public abstract int getCharType(char c);

    public abstract void initializeCharType();

    public abstract int getMaxChoices(char c, Collection<Character> choices);
    public abstract int findCharWithType(int type, Collection<Character> chars);

    public boolean hasOutEdge(char c){
        char sub;
        boolean hasOutEdge = graph.containsEdge(c);
        return hasOutEdge || c != (sub = KoreanUtils.getSubChar(c)) && graph.containsEdge(sub);
    }

    public static int getTypePriority(int i){
        int ret;
        if(i < 0)
            ret = 0; // neutral word for 0
        else if((i & 1) == 0){
            ret = Integer.MAX_VALUE & ~i; // n State
        } else {
            ret =  i | Integer.MIN_VALUE; // p State
        }
        return ret;
    }

    public int getPrevType(char c){
        int prev = getCharType(c);
        if(prev > 0)
            --prev;
        return prev;
    }
}
