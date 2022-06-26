package kr.ac.snu.sbkim28.analyze;

/**
 * 그래프에서 Character를 인덱스로 반환하고,
 * 인덱스를 Character로 반환함.
 * @author sbkim28
 * @version 1.0.1
 * @see WordGraphGenerator
 */
public interface Indexer {
    /**
     * Character에 대응되는 matrix의 인덱스를 반환함.
     * @return 파라미터 c에 대응되는 인덱스. c에 대응되는 인덱스가 존재하지 않으면 -1을 반환함.
     */
    int getIndex(char c);

    /**
     * matrix의 인덱스에 대응되는 Character을 반환함.
     * @throws ArrayIndexOutOfBoundsException
     * 만약 파라미터로 주어진 index가 범위에 벗어날 경우.
     */
    char getChar(int index);

    /**
     * Indexer에 추가된 character의 종류의 개수를 반환함.
     */
    int getLength();

    /**
     * Character c를 추가함. c에 대응되는 인덱스를 생성함.
     * 만약 이미 c가 존재한다면 별도로 인덱스를 새로 생성하지 않음.
     */
    void addChar(char c);
}
