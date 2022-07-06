package kr.ac.snu.sbkim28.analyze;

public class MatrixGraph implements Graph{
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


}
