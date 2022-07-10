package kr.ac.snu.sbkim28.analyze.graph;

import java.util.Arrays;

/**
 * 메트릭스에 사용될 인접행렬.
 * <p>
 * AdjacentMatrix 클래스는 제한된 크기 length를 가지고 있으며,
 * length × length 크기의 인접행렬을 만들고 관리한다.
 * </p>
 * <p>
 * 인접행렬에서 get, set, add, remove는 상수 시간 내에 수행된다.
 * copy와 removeLine, resize는 length^2의 시간이 소요된다.
 * </p>
 */
public class AdjacentMatrix implements Matrix{
    private int[] matrix;
    private int length;

    /**
     * Constructor
     * @param length param length.
     */
    public AdjacentMatrix(int length){
        this.length = length;
        matrix = new int[length * length];
    }

    /**
     * <p>(i, j)번째 원소를 반환한다.</p>
     * @param i row
     * @param j col
     * @return (i, j) element
     */
    @Override
    public int get(int i, int j){
        return matrix[i * length + j];
    }

    /**
     * length를 반환한다.
     * @return length.
     */
    @Override
    public int length() {
        return length;
    }


    /**
     * <p>
     *     (i, j)번째 원소를 v로 설정한다.
     * </p>
     * @param i row
     * @param j col
     * @param v value
     */
    @Override
    public boolean set(int i, int j, int v){
        boolean ret = checkLength(i, j);
        if(ret)
            matrix[i * length + j] = v;
        return ret;
    }

    /**
     * <p>
     *     (i, j)번째 원소에 1을 더한다.
     * </p>
     * @param i row
     * @param j col
     */
    @Override
    public boolean add(int i, int j){
        boolean ret = checkLength(i, j);
        if(ret)
            ++matrix[i * length + j];
        return ret;
    }

    /**
     * <p>
     *     (i, j)번째 원소에 1을 뺀다. (i, j)번째 원소가 0이라면 변화를 가하지 않는다.
     * </p>
     * @param i row
     * @param j col
     * @return 만약 (i, j)번째 원소가 0이 아니라면 true, 그렇지 않다면 false.
     */
    @Override
    public boolean remove(int i, int j){
        boolean ret = checkLength(i, j);
        if(ret)
            --matrix[i * length + j];

        return ret;
    }

    private boolean checkLength(int i, int j) {
        boolean iIn = i >= 0 && i < length;
        return iIn && j >= 0 && j < length;
    }

    /**
     * <p>
     *     index에 해당하는 row와 column을 제거한다. 제거한 자리를 0으로 비워두는 것이 아니라,
     *     행렬에서 1칸씩 원소를 이동시켜 빈 자리를 채운다.
     * </p>
     * @param index index
     */
    @Override
    public boolean removeLine(int index){
        boolean ret = index >= 0 && index < length;
        if(ret) {
            for (int i = index + 1; i < length; ++i) {
                for (int j = 0; j < index; ++j) {
                    set(i - 1, j, get(i, j));
                    set(j, i - 1, get(j, i));
                }
                for (int j = index + 1; j < length; ++j) {
                    set(i - 1, j - 1, get(i, j));
                }
            }
            int last = length - 1;
            for (int i = 0; i < last; ++i) {
                set(i, last, 0);
                set(last, i, 0);
            }
        }
        return ret;
    }

    /**
     * AdjacentMatrix를 복사해서 새로운 Matrix를 만든다.
     * @return new AdjacentMatrix.
     */
    public AdjacentMatrix copy(){
        AdjacentMatrix newMatrix = new AdjacentMatrix(length);
        System.arraycopy(matrix, 0, newMatrix.matrix, 0, length * length);
        return newMatrix;
    }

    /**
     * size 크기의 새로운 length를 갖는 Matrix를 만들고, 기존 데이터를 복사한다.
     * @param size new length
     * @return new AdjacentMatrix
     */
    @Override
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
