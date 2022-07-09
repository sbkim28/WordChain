package kr.ac.snu.sbkim28.analyze.graph;

import java.util.Iterator;

public interface Vertex<T extends Vertex<T>>
        extends Iterable<T>{
    char getVertexChar();
    boolean isEmpty();
    int size();
}
