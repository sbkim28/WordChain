package kr.ac.snu.sbkim28.analyze;

import kr.ac.snu.sbkim28.analyze.graph.LinkedWordGraph;
import kr.ac.snu.sbkim28.analyze.graph.Vertex;
import kr.ac.snu.sbkim28.util.KoreanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkedCharTypeFinder extends CharTypeFinder {

    private LinkedWordGraph<?> graph;
    private Map<Character, Integer> charType;

    public LinkedCharTypeFinder(LinkedWordGraph<?> graph) {
        super(graph);
        this.graph = graph;
    }

    @Override
    public int getCharType(char c) {
        return charType.get(c);
    }

    @Override
    public void initializeCharType() {
        int length = graph.vertexSize();
        charType = new HashMap<>((int) (length / 0.75 + 1));
        findCharWithoutEdge();
        boolean isSomethingChanged;
        do {
            isSomethingChanged = setCharTypeByPriority();
        } while (isSomethingChanged);
    }

    private void findCharWithoutEdge(){
        for (Vertex<?> v : graph){
            char c = v.getVertexChar();
            charType.put(c, hasOutEdge(c) ? -1 : 0);
        }
    }

    private boolean setCharTypeByPriority() {
        boolean ret = false;
        for (Vertex<?> v : graph){
            if(charType.get(v.getVertexChar()) != 0)
                ret |= setCharTypeByPriority(v);
        }
        return ret;
    }

    @Override
    public List<Character> getMaxChoices(char c){
        Vertex<?> v = graph.getVertex(c);
        char sub = KoreanUtils.getSubChar(c);
        Vertex<?> subV;
        int prev = getCharType(c);
        if(prev > 0)
            --prev;

        List<Character> ret = new ArrayList<>(prev > 0 ? 1 << 4 : 1 << 5);
        addCharToList(ret, v, prev);
        if(sub != c && (subV = graph.getVertex(sub)) != null){
            addCharToList(ret, subV, prev);
        }
        return ret;
    }

    @Override
    public List<Character> findCharWithType(int type) {
        List<Character> ret = new ArrayList<>(1 << 8);

        return ret;
    }

    private int maxPriority;
    private int maxType;
    private boolean setCharTypeByPriority(Vertex<?> vertex){
        char c = vertex.getVertexChar();
        char subChar = KoreanUtils.getSubChar(c);
        maxPriority = Integer.MIN_VALUE;
        maxType = 1;

        setPriority(vertex);
        Vertex<?> sub;
        if(subChar != c && (sub = graph.getVertex(subChar)) != null){
            setPriority(sub);
        }

        boolean ret = false;
        if(maxPriority != 0){
            int next = maxType + 1;
            ret = next != charType.get(c);
            charType.put(c, next);
        }
        return ret;
    }

    private void setPriority(Vertex<?> vertex){
        for (Vertex<?> to: vertex){
            int type = charType.get(to.getVertexChar());
            int priority = CharTypeFinder.getTypePriority(type);
            if(priority > maxPriority){
                maxPriority = priority;
                maxType = type;
            }
        }
    }

    private void addCharToList(List<Character> ret, Vertex<?> v, int target){
        for (Vertex<?> to : v){
            char toC = to.getVertexChar();
            if(getCharType(toC) == target){
                ret.add(toC);
            }
        }
    }
}
