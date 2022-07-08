package kr.ac.snu.sbkim28.analyze;

import java.util.Iterator;

public interface Graph<T extends VertexIterator<T>> extends Iterable<T>{
    void addVertex(char c);
    void removeVertex(char c);
    void addEdge(char from, char to);
    boolean removeEdge(char from, char to);
    boolean containsVertex(char c);
    boolean containsEdge(char from, char to);
    int edgeCount(char c);
    int edgeCount(char from, char to);
    int vertexSize();

    T getVertex(char c);
    @Override
    Iterator<T> iterator();


}