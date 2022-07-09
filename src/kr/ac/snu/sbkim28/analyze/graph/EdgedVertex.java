package kr.ac.snu.sbkim28.analyze.graph;

import java.util.Iterator;
import java.util.LinkedList;

public class EdgedVertex implements Vertex<EdgedVertex> {

    private char vertexChar;
    private LinkedList<Edge> edges;

    public EdgedVertex(char vertexChar) {
        this.vertexChar = vertexChar;
        edges = new LinkedList<>();
    }

    @Override
    public char getVertexChar() {
        return vertexChar;
    }

    @Override
    public Iterator<EdgedVertex> iterator() {
        return new VertexIterAdaptor(edges.iterator());
    }

    public Iterator<Edge> edgeIterator(){
        return edges.iterator();
    }

    public int size() {
        return edges.size();
    }

    public boolean add(Edge edge) {
        return edges.add(edge);
    }

    public boolean remove(Edge edge) {
        return edges.remove(edge);
    }

    public boolean remove(EdgedVertex vertex){
        Iterator<Edge> iter = edgeIterator();
        boolean removed = false;
        while (iter.hasNext()){
            Edge e = iter.next();
            if(e.getFinalVertex() == vertex){
                iter.remove();
                removed = true;
                break;
            }
        }
        return removed;
    }

    public boolean remove(String s){
        Iterator<Edge> iter = edgeIterator();
        boolean removed = false;
        while (iter.hasNext()){
            Edge e = iter.next();
            if(e.getWord().equals(s)){
                iter.remove();
                removed = true;
                break;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        return edges.isEmpty();
    }

    public boolean contains(Edge edge){
        return edges.contains(edge);
    }

    public boolean contains(String s){
        Iterator<Edge> iter = edgeIterator();
        boolean contains = false;
        while (iter.hasNext()){
            if(iter.next().getWord().equals(s)){
                contains = true;
                break;
            }
        }
        return contains;
    }

    @Override
    public String toString() {
        return "Vertex[" + vertexChar + ']';
    }

    private static final class VertexIterAdaptor implements Iterator<EdgedVertex> {
        private Iterator<? extends Edge> buff;

        private VertexIterAdaptor(Iterator<? extends Edge> buff) {
            this.buff = buff;
        }

        @Override
        public boolean hasNext() {
            return buff.hasNext();
        }

        @Override
        public EdgedVertex next() {
            return buff.next().getFinalVertex();
        }

        @Override
        public void remove() {
            buff.remove();
        }
    }

}
