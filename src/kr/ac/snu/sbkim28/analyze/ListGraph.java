package kr.ac.snu.sbkim28.analyze;

import java.util.*;

public class ListGraph implements Graph {

    private Map<Character, GraphVertex> vertexMap;

    public ListGraph() {
        this.vertexMap = new HashMap<>();
    }
    public ListGraph(int length){
        this.vertexMap = new HashMap<>((int) (length / 0.75 + 1));
    }

    @Override
    public void addVertex(char c){
        if(!vertexMap.containsKey(c))
            vertexMap.put(c, new GraphVertex(c));
    }

    @Override
    public void removeVertex(char c){
        GraphVertex target = vertexMap.remove(c);
        if(target != null) {
            for (GraphVertex v : vertexMap.values()) {
                Iterator<GraphVertex> iter = v.iterator();
                while (iter.hasNext()){
                    GraphVertex to = iter.next();
                    if(to == target)
                        iter.remove();
                }
            }
        }
    }

    @Override
    public void addEdge(char from, char to){
        vertexMap.get(from).add(vertexMap.get(to));
    }

    @Override
    public boolean removeEdge(char from, char to){
        return vertexMap.get(from).remove(vertexMap.get(to));
    }

    @Override
    public boolean containsVertex(char c){
        return vertexMap.containsKey(c);
    }

    @Override
    public int edgeCount(char from){
        return vertexMap.get(from).size();
    }

    @Override
    public boolean containsEdge(char from, char to) {
        GraphVertex v = vertexMap.get(from);
        for (GraphVertex toVertex: v) {
            if(toVertex.c == to)
                return true;
        }
        return false;
    }

    @Override
    public int edgeCount(char from, char to) {
        int cnt = 0;
        GraphVertex v = vertexMap.get(from);
        for (GraphVertex toVertex: v) {
            if(toVertex.c == to)
                ++cnt;
        }
        return cnt;
    }

    public GraphVertex getVertex(char from){
        return vertexMap.get(from);
    }

    public static class GraphVertex extends ArrayList<GraphVertex>{
        private char c;

        public GraphVertex(char c) {
            super();
            this.c = c;
        }

        public char getChar() {
            return c;
        }

        @Override
        public boolean add(GraphVertex graphVertex) {
            if(graphVertex == null)
                throw new IllegalArgumentException("null not allowed");
            return super.add(graphVertex);
        }
    }
}
