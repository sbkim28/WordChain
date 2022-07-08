package kr.ac.snu.sbkim28.analyze;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixGraph implements Graph<MatrixGraph.MatrixVertexIterator> {
    private AdjacentMatrix matrix;
    private Indexer indexer;

    private int capacity;
    private int vertexSize;

    public MatrixGraph() {
        this(16);
    }

    public MatrixGraph(int capacity){
        this.capacity = capacity;
        indexer = new KoreanWordIndexer();
        matrix = new AdjacentMatrix(capacity);
    }

    @Override
    public void addVertex(char c){
        if(indexer.addChar(c)){
            ++vertexSize;
            if(vertexSize > capacity){
                matrix = matrix.resize(capacity *= 2);
            }
        }
    }

    @Override
    public void removeVertex(char c) {
        int index = indexer.getIndex(c);
        if(index != -1){

            for (int i = index + 1; i < vertexSize; ++i) {
                for (int j = 0; j < index; ++j) {
                    matrix.set(i - 1, j, matrix.get(i, j));
                    matrix.set(j, i - 1, matrix.get(j, i));
                }
                for (int j = index + 1; j < vertexSize; ++j) {
                    matrix.set(i - 1, j - 1, matrix.get(i, j));
                }
            }
            int last = vertexSize - 1;
            for (int i = 0; i < vertexSize; ++i) {
                matrix.set(i, last, 0);
                matrix.set(last, i, 0);
            }

            indexer.removeChar(c);
            --vertexSize;
        }
    }

    @Override
    public void addEdge(char from, char to) {
        matrix.add(indexer.getIndex(from), indexer.getIndex(to));
    }

    @Override
    public boolean removeEdge(char from, char to) {
        return matrix.remove(indexer.getIndex(from), indexer.getIndex(to));
    }

    @Override
    public boolean containsVertex(char c) {
        return indexer.containsChar(c);
    }

    @Override
    public boolean containsEdge(char from, char to) {
        return matrix.get(indexer.getIndex(from), indexer.getIndex(to)) > 0;
    }

    @Override
    public int edgeCount(char c) {
        int sum = 0;
        for (int i = 0; i < vertexSize; ++i) {
            sum += matrix.get(indexer.getIndex(c), i);
        }
        return sum;
    }

    @Override
    public int edgeCount(char from, char to) {
        return matrix.get(indexer.getIndex(from), indexer.getIndex(to));
    }

    @Override
    public int vertexSize() {
        return vertexSize;
    }

    @Override
    public MatrixVertexIterator getVertex(char c) {
        return new MatrixVertexIterator(c);
    }

    @Override
    public Iterator<MatrixVertexIterator> iterator() {
        return new Iterator<>() {
            private int cursor = 0;
            @Override
            public boolean hasNext() {
                return cursor < vertexSize;
            }

            @Override
            public MatrixVertexIterator next() {
                if(!hasNext())
                    throw new NoSuchElementException();
                return new MatrixVertexIterator(cursor);
            }
        };
    }

    public class MatrixVertexIterator implements VertexIterator<MatrixVertexIterator> {
        private char c;
        private int index;
        private MatrixVertexIterator(char c) {
            this.c = c;
            index = indexer.getIndex(c);
        }

        private MatrixVertexIterator(int index){
            this.index = index;
            c = indexer.getChar(index);
        }

        @Override
        public char getVertexChar() {
            return c;
        }
        @Override
        public Iterator<MatrixVertexIterator> iterator() {
            return new Iterator<>() {
                private int cursor = 0;
                private MatrixVertexIterator holder;
                private boolean setCursor(){
                    while (cursor < vertexSize && matrix.get(index, cursor) != 0){
                        ++cursor;
                    }
                    return cursor < vertexSize;
                }
                @Override
                public boolean hasNext() {
                    boolean ret = setCursor();
                    if(ret)
                        holder = new MatrixVertexIterator(cursor);
                    return ret;
                }

                @Override
                public MatrixVertexIterator next() {
                    if(holder == null) {
                        if(!setCursor()){
                            throw new NoSuchElementException();
                        }
                        holder = new MatrixVertexIterator(cursor);
                        ++cursor;
                    }
                    return holder;
                }
            };
        }
    }

}
