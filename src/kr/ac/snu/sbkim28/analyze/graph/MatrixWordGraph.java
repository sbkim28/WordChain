package kr.ac.snu.sbkim28.analyze.graph;

import java.util.NoSuchElementException;

public class MatrixWordGraph implements IndexedGraph {
    private Matrix matrix;
    private Indexer indexer;

    private int capacity;
    private int vertexSize;

    public MatrixWordGraph() {
        this(16);
    }

    public MatrixWordGraph(int capacity){
        super();
        this.capacity = capacity;
        vertexSize = 0;
        indexer = new KoreanWordIndexer();
        matrix = new AdjacentMatrix(capacity);
    }

    private MatrixWordGraph(Matrix matrix, Indexer indexer) {
        super();
        this.matrix = matrix;
        this.indexer = indexer;
        vertexSize = indexer.getLength();
        capacity = vertexSize;
    }

    public static MatrixWordGraph createNew(Iterable<String> data){
        Indexer indexer = new KoreanWordIndexer();
        int len = createIndexer(indexer, data);
        AdjacentMatrix mat = new AdjacentMatrix(len);
        for (String word : data){
            mat.add(indexer.getIndex(word.charAt(0)),
                    indexer.getIndex(word.charAt(word.length() - 1)));
        }
        return new MatrixWordGraph(mat, indexer);
    }

    private static int createIndexer(Indexer indexer, Iterable<String> data){
        for (String word: data){
            indexer.addChar(word.charAt(0));
            indexer.addChar(word.charAt(word.length() - 1));
        }
        return indexer.getLength();
    }

    @Override
    public Indexer getIndexer() {
        return new IndexerDelegator(indexer);
    }

    @Override
    public Matrix getMatrix() {
        return new MatrixDelegator(matrix);
    }

    @Override
    public boolean addVertex(char c){
        boolean added = indexer.addChar(c);
        if(added){
            ++vertexSize;
            if(vertexSize > capacity){
                matrix = matrix.resize(capacity <<= 1);
            }
        }
        return added;
    }

    @Override
    public boolean removeVertex(char c) {
        int index = indexer.getIndex(c);
        boolean ret = index != -1;
        if(ret){
            matrix.removeLine(index);
            indexer.removeChar(c);
            --vertexSize;
        }
        return ret;
    }

    @Override
    public boolean addEdge(char from, char to) {
        boolean ret = indexer.containsChar(from) && indexer.containsChar(to);
        if(ret)
            matrix.add(indexer.getIndex(from), indexer.getIndex(to));
        return ret;
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
    public boolean containsEdge(char c) {
        int index = indexer.getIndex(c);
        boolean ret = false;
        if(index != -1) {
            for (int i = 0; i < vertexSize; ++i) {
                if (matrix.get(index, i) > 0){
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean containsEdge(char from, char to) {
        int fromIndex = indexer.getIndex(from);
        int toIndex = indexer.getIndex(to);

        return fromIndex != -1 && toIndex != -1 &&
                matrix.get(fromIndex, toIndex) > 0;
    }

    @Override
    public boolean containsEdge(int index) {
        boolean ret = false;
        for (int i = 0; i < vertexSize; ++i) {
            if (matrix.get(index, i) > 0){
                ret = true;
                break;
            }
        }
        return ret;
    }

    @Override
    public boolean containsEdge(int from, int to) {
        return edgeCount(from, to) > 0;
    }

    @Override
    public int edgeCount(char c) {
        int sum = 0;
        int index = indexer.getIndex(c);
        if(index < 0)
            throw new NoSuchElementException("No vertex: " + c);
        for (int i = 0; i < vertexSize; ++i) {
            sum += matrix.get(index, i);
        }
        return sum;
    }

    @Override
    public int edgeCount(char from, char to) {
        int fromIndex = indexer.getIndex(from);
        int toIndex = indexer.getIndex(to);
        if(fromIndex == -1 || toIndex == -1)
            throw new NoSuchElementException("No vertex from: " +from + ", to: " + to);
        return matrix.get(fromIndex, toIndex);
    }

    @Override
    public int edgeCount(int index) {
        if(index < 0 || index >= vertexSize)
            throw new IndexOutOfBoundsException(index);
        int sum = 0;
        for (int i = 0; i < vertexSize; ++i) {
            sum += matrix.get(index, i);
        }
        return sum;
    }

    @Override
    public int edgeCount(int from, int to) {
        if(from < 0 || from >= vertexSize)
            throw new IndexOutOfBoundsException(from);
        if(to < 0 || to >= vertexSize)
            throw new IndexOutOfBoundsException(to);

        return matrix.get(from, to);
    }

    @Override
    public int vertexSize() {
        return vertexSize;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder((1 + vertexSize) * 20);
        builder.append(getClass().getSimpleName())
                .append('\n');
        for (int i = 0; i < vertexSize; ++i){
            builder.append(indexer.getChar(i));
            builder.append(": ");
            boolean isFirstAppend = true;
            for (int j = 0; j < vertexSize; ++j) {
                int cnt = matrix.get(i, j);
                if(cnt != 0){
                    if(!isFirstAppend) {
                        builder.append(", ");
                    }
                    isFirstAppend = false;
                    builder.append(indexer.getChar(j));
                    builder.append('[');
                    builder.append(cnt);
                    builder.append(']');
                }
            }
            if(isFirstAppend)
                builder.append("null");
            builder.append('\n');
        }
        return builder.toString();
    }

    private static final class MatrixDelegator implements Matrix {
        private Matrix matrix;

        private MatrixDelegator(Matrix matrix) {
            this.matrix = matrix;
        }

        @Override
        public int get(int i, int j) {
            return matrix.get(i, j);
        }

        @Override
        public int length() {
            return matrix.length();
        }

        @Override
        public boolean set(int i, int j, int v) {
            return false;
        }

        @Override
        public boolean add(int i, int j) {
            return false;
        }

        @Override
        public boolean remove(int i, int j) {
            return false;
        }

        @Override
        public boolean removeLine(int index) {
            return false;
        }

        @Override
        public Matrix resize(int size) {
            return matrix.resize(size);
        }
    }
    private static final class IndexerDelegator implements Indexer {
        private Indexer indexer;

        private IndexerDelegator(Indexer indexer) {
            this.indexer = indexer;
        }

        @Override
        public int getIndex(char c) {
            return indexer.getIndex(c);
        }

        @Override
        public char getChar(int index) {
            return indexer.getChar(index);
        }

        @Override
        public int getLength() {
            return indexer.getLength();
        }

        @Override
        public boolean addChar(char c) {
            return false;
        }

        @Override
        public boolean containsChar(char c) {
            return indexer.containsChar(c);
        }

        @Override
        public boolean removeChar(char c) {
            return false;
        }
    }
}
