package kr.ac.snu.sbkim28.analyze;

import java.util.*;

public class ListGraph implements Graph<ListGraph.GraphVertex> {

    private Map<Character, GraphVertex> vertexMap;
    public ListGraph() {
        this.vertexMap = new LinkedHashMap<>();
    }
    public ListGraph(int length){
        this.vertexMap = new LinkedHashMap<>((int) (length / 0.75 + 1));
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

    @Override
    public int vertexSize() {
        return vertexMap.size();
    }

    @Override
    public GraphVertex getVertex(char c){
        return vertexMap.get(c);
    }

    @Override
    public Iterator<GraphVertex> iterator() {
        return vertexMap.values().iterator();
    }

    public static class GraphVertex extends ArrayList<GraphVertex>
    implements VertexIterator<GraphVertex>{
        private char c;

        private GraphVertex(char c) {
            super();
            this.c = c;
        }
        @Override
        public char getVertexChar() {
            return c;
        }

        @Override
        public Iterator<GraphVertex> iterator() {
            return super.iterator();
        }

        @Override
        public boolean add(GraphVertex graphVertex) {
            if(graphVertex == null)
                throw new IllegalArgumentException("null not allowed");
            return super.add(graphVertex);
        }
    }
}
