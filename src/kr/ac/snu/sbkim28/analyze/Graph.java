package kr.ac.snu.sbkim28.analyze;

public interface Graph {
    void addVertex(char c);
    void removeVertex(char c);
    void addEdge(char from, char to);
    boolean removeEdge(char from, char to);
    boolean containsVertex(char c);
    boolean containsEdge(char from, char to);
    int edgeCount(char c);
    int edgeCount(char from, char to);

    // todo iterator method
}