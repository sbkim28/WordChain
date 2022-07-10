package kr.ac.snu.sbkim28.analyze.graph;

public interface IndexedGraph extends WordGraph{

    Indexer getIndexer();
    Matrix getMatrix();

    int edgeCount(int index);
    int edgeCount(int from, int to);
}
