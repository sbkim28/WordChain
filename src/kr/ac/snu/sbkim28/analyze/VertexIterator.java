package kr.ac.snu.sbkim28.analyze;

import java.util.Iterator;

public interface VertexIterator<T extends VertexIterator<T>>
        extends Iterable<T> {

    char getVertexChar();

    @Override
    Iterator<T> iterator();
}
