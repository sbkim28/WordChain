package kr.ac.snu.sbkim28.analyze.graph;

public interface Matrix {
    int get(int i, int j);
    int length();
    boolean set(int i, int j, int v);
    boolean add(int i, int j);
    boolean remove(int i, int j);
    boolean removeLine(int index);
    Matrix resize(int size);
}
