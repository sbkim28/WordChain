package kr.ac.snu.sbkim28.analyze;

public class WordMatrix implements Cloneable{
    private int[] matrix;
    private int len;

    public WordMatrix(int len){
        this.len = len;
        matrix = new int[len * len];
    }

    public int get(int i, int j){
        return matrix[i * len + j];
    }

    public int length() {
        return len;
    }

    public void set(int i, int j, int v){
        matrix[i * len + j] = v;
    }

    public void add(int i, int j){
        ++matrix[i * len + j];
    }

    public void subtract(int i, int j){
        --matrix[i * len + j];
    }

    @Override
    public WordMatrix clone() {
        WordMatrix newMatrix = new WordMatrix(len);
        System.arraycopy(matrix, 0, newMatrix.matrix, 0, len * len);
        return newMatrix;
    }
}
