package kr.ac.snu.sbkim28.analyze.graph;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class LinkedWordGraph<V extends Vertex<V>> implements WordGraph, Iterable<V>{
    private Map<Character, V> vertexMap;
    protected LinkedWordGraph() {
        //noinspection CollectionWithoutInitialCapacity
        vertexMap = new LinkedHashMap<>();
    }

    protected LinkedWordGraph(int length){
        vertexMap = new LinkedHashMap<>((int) (length / 0.75 + 1));
    }

    protected boolean putVertex(char c, V v){
        boolean put = containsVertex(c);
        if(!put)
            vertexMap.put(c, v);
        return put;
    }

    @Override
    public boolean removeVertex(char c){
        V target = vertexMap.remove(c);
        boolean ret = target != null;
        if(ret) {
            for (V v : vertexMap.values()) {
                Iterator<V> iter = v.iterator();
                while (iter.hasNext()){
                    V to = iter.next();
                    if(to == target)
                        iter.remove();
                }
            }
        }
        return ret;
    }

    @Override
    public boolean containsVertex(char c){
        return vertexMap.containsKey(c);
    }

    @Override
    public boolean containsEdge(char c) {
        V v = vertexMap.get(c);
        return v != null && !v.isEmpty();
    }

    @Override
    public boolean containsEdge(char from, char to) {
        V v = vertexMap.get(from);
        V target = vertexMap.get(to);
        boolean ret = false;
        if(v != null && target != null) {
            for (V toVertex : v) {
                if (toVertex == target) {
                    ret = true;
                    break;
                }
            }
        }

        return ret;
    }

    @Override
    public int edgeCount(char c) {
        V v = vertexMap.get(c);
        if(v == null)
            throw new NoSuchElementException("No vertex: " + c);
        return v.size();
    }

    @Override
    public int edgeCount(char from, char to) {
        int cnt = 0;
        V v = vertexMap.get(from);
        V target = vertexMap.get(to);
        if(v == null || target == null)
            throw new NoSuchElementException("No vertex from: " + from + ", to: " + to);
        for (V toVertex: v) {
            if(toVertex == target)
                ++cnt;
        }
        return cnt;
    }

    @Override
    public int vertexSize() {
        return vertexMap.size();
    }
    public V getVertex(char c){
        return vertexMap.get(c);
    }

    public Iterator<V> iterator() {
        return vertexMap.values().iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(vertexSize() * 20);
        builder.append(getClass().getSimpleName()).append('\n');
        for (V vertex: this) {
            builder.append(vertex.getVertexChar());
            builder.append(": ");
            boolean isFirst = true;
            for(V to : vertex){
                if(!isFirst){
                    builder.append(", ");
                }
                isFirst = false;
                builder.append(to.getVertexChar());
            }
            if(isFirst)
                builder.append("null");
            builder.append('\n');
        }
        return builder.toString();
    }
}
