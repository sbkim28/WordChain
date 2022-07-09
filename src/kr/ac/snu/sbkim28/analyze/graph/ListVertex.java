package kr.ac.snu.sbkim28.analyze.graph;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.LinkedList;

public class ListVertex extends AbstractList<ListVertex>
        implements Vertex<ListVertex> {
    private final LinkedList<ListVertex> vertexList;
    private char vertexChar;

    public ListVertex(char vertexChar) {
        super();
        vertexList = new LinkedList<>();
        this.vertexChar = vertexChar;
    }
    @Override
    public char getVertexChar() {
        return vertexChar;
    }

    @Override
    public int size() {
        return vertexList.size();
    }

    @Override
    public boolean isEmpty() {
        return vertexList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return vertexList.contains(o);
    }

    @Override
    public boolean add(ListVertex listVertex) {
        return vertexList.add(listVertex);
    }

    @Override
    public ListVertex get(int index) {
        return vertexList.get(index);
    }

    @Override
    public boolean remove(Object o) {
        return vertexList.remove(o);
    }

    @Override
    public Iterator<ListVertex> iterator() {
        return vertexList.iterator();
    }

    @Override
    public String toString() {
        return "Vertex[" + vertexChar + ']';
    }
}