package kr.ac.snu.sbkim28.analyze.graph;

public interface IndexedGraph extends WordGraph{

    Indexer getIndexer();
    Matrix getMatrix();

    boolean containsEdge(int index);
    boolean containsEdge(int from, int to);
    int edgeCount(int index);
    int edgeCount(int from, int to);
}
