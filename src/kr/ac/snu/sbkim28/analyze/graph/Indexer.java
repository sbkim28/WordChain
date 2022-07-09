package kr.ac.snu.sbkim28.analyze.graph;

/**
 * 그래프에서 Character를 인덱스로 반환하고,
 * 인덱스를 Character로 반환함.
 * 인접행렬을 이용한 그래프에서 각 행과 열의 인덱스에 대응되는
 * Character가 뭔지 가리키기 위해서 사용함.
 * @author sbkim28
 * @version 1.0.1
 * @see MatrixWordGraph
 */
public interface Indexer {
    /**
     * Character에 대응되는 matrix의 인덱스를 반환함.
     * @param c character c
     * @return 파라미터 c에 대응되는 인덱스. c에 대응되는 인덱스가 존재하지 않으면 -1을 반환함.
     */
    int getIndex(char c);

    /**
     * matrix의 인덱스에 대응되는 Character을 반환함.
     * @param index int index
     * @throws ArrayIndexOutOfBoundsException
     * 만약 파라미터로 주어진 index가 범위에 벗어날 경우.
     */
    char getChar(int index);

    /**
     * Indexer에 추가된 character의 종류의 개수를 반환함.
     * @return indexer의 length
     */
    int getLength();

    /**
     * Character c를 추가함. c에 대응되는 인덱스를 생성함.
     * 만약 이미 c가 존재한다면 별도로 인덱스를 새로 생성하지 않음.
     * @return c가 존재하여 indexer에 변화가 없으면 false, c가 존재하지 않아서 새로 생성하였다면 true
     */
    boolean addChar(char c);

    /**
     * Character가 indexer에 존재하는지를 확인함.
     * @param c character c
     * @return param c가 indexer에 있다면 true, 없다면 false.
     */
    boolean containsChar(char c);

    /**
     * Character을 indexer에서 제거하고, 기존 Character의 index을 제거된 index에 따라서 수정함.
     * 만약 Character가 indexer에 존재하지 않는다면 별도의 작업을 수행하지 않음.
     * @param c character c
     * @return param c가 존재하지 않아서 indexer에 변화가 없으면 false. c가 존재하여 제거하였다면 true.
     */
    boolean removeChar(char c);
}
