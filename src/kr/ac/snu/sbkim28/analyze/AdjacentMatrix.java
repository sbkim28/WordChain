package kr.ac.snu.sbkim28.analyze;

import java.util.Arrays;

/**
 * 메트릭스에 사용될 인접행렬.
 * <p>
 * AdjacentMatrix 클래스는 제한된 크기 length를 가지고 있으며,
 * length × length 크기의 인접행렬을 만들고 관리한다.
 * </p>
 * <p>
 * 인접행렬에서 get, set, add, remove는 상수 시간 내에 수행된다.
 * copy와 resize는 length^2의 시간이 소요된다.
 * </p>
 */
public class AdjacentMatrix {
    private int[] matrix;
    private int length;

    public AdjacentMatrix(int length){
        this.length = length;
        matrix = new int[length * length];
    }

    public int get(int i, int j){
        return matrix[i * length + j];
    }

    public int length() {
        return length;
    }

    public void set(int i, int j, int v){
        matrix[i * length + j] = v;
    }

    public void add(int i, int j){
        ++matrix[i * length + j];
    }

    public boolean remove(int i, int j){
        int index = i * length + j;
        boolean ret = index > 0;
        if(ret) {
            --matrix[i * length + j];
        }
        return ret;
    }

    public AdjacentMatrix copy(){
        AdjacentMatrix newMatrix = new AdjacentMatrix(length);
        System.arraycopy(matrix, 0, newMatrix.matrix, 0, length * length);
        return newMatrix;
    }

    public AdjacentMatrix resize(int size){
        if(size < length)
            throw new IllegalArgumentException("size smaller than length");
        AdjacentMatrix newMatrix = new AdjacentMatrix(size);

        for (int i = 0; i <length; ++i){
            System.arraycopy(matrix, i * length,
                    newMatrix.matrix, i * size, length);
        }
        return newMatrix;
    }

    @Override
    public String toString() {
        return Arrays.toString(matrix);
    }
}
