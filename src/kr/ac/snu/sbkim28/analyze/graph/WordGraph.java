package kr.ac.snu.sbkim28.analyze.graph;

import java.util.NoSuchElementException;

public interface WordGraph {
    /**
     * <p>
     *     Vertex를 추가한다. 이미 해당 char을 갖는 Vertex가 존재하면 무시한다.
     * </p>
     * @param c Vertex의 character
     * @return 이미 해당 character의 Vertex가 존재하면 false, 그렇지 않고 Vertex를 추가하였다면 true.
     */
    boolean addVertex(char c);

    /**
     * <p>
     *     Vertex를 제거한다. 해당 char에 대응되는 Vertex가 존재하지 않는다면 무시한다.
     * </p>
     * @param c Vertex의 character
     * @return 해당 character을 가진 Vertex가 존재하지 않는다면 false, 그렇지 않고 Vertex를 제거하였다면 true.
     */
    boolean removeVertex(char c);


    /**
     * <p>
     *     Edge를 추가한다. from에 대응되는 Vertex를 시점, to에 대응되는 Vertex를 종점으로 Edge를 추가한다.
     *     추가하는 Edge에서는 중복이 허용되며, from과 to가 같은 경우도
     *     허용된다.
     * </p>
     * <p>
     *     from과 to에 대응되는 Vertex가 존재하지 않는다면
     *     Edge를 추가하지 않고 아무런 변경을 가하지 않는다.
     * </p>
     * @param from 시점
     * @param to 종점
     * @return from과 to에 대응되는 Vertex가 존재하여 Edge를 추가하였다면 true, 그렇지 않았다면 false.
     */
    boolean addEdge(char from, char to);

    /**
     * <p>
     *     Edge를 제거한다. from에 대응되는 Vertex를 시점, to에 대응되는 Vertex를 종점으로
     *     하는 Edge를 제거한다. 제거할 때 그러한 Edge가 여러 개 존재한다면
     *     그 중에서 하나만 제거한다.
     * </p>
     * <p>
     *     from과 to에 대응되는 Vertex가 존재하지 않거나,
     *     from에서 to로 가는 Edge가 존재하지 않는다면
     *     아무런 변경을 가하지 않는다.
     * </p>
     * @param from 시점
     * @param to 종점
     * @return from과 to에 대응되는 Vertex 및 Edge가 존재하여 Edge를 제거하였다면 true, 그렇지 않았다면 false.
     */
    boolean removeEdge(char from, char to);

    /**
     * <p>
     *      character c에 대응되는 Vertex가 존재하는지를 확인하여 반환한다.
     * </p>
     * @param c Vertex의 character
     * @return c에 대응되는 Vertex가 존재하면 true, 그렇지 않다면 false
     */
    boolean containsVertex(char c);

    /**
     * <p>
     *     character c에 대응되는 Vertex가 Edge를 가졌는지를 확인하여 반환한다.
     *     Vertex가 Edge를 갖는다는 것은 이 Vertex를 시점으로 하는 Edge를 갖는지를
     *     검사하는 것이다.
     * </p>
     * <p>
     *     만약 c에 대응되는 Vertex가 없다면 false를 반환한다.
     * </p>
     * @param c Vertex의 character
     * @return c에 대응되는 Vertex가 존재하고 그 Vertex가 edge를 갖는다면 true, 그렇지 않다면 false.
     */
    boolean containsEdge(char c);

    /**
     * <p>
     *     from에 대응되는 Vertex를 시점, to에 대응되는 Vertex를 종점으로 하는
     *     Edge가 존재하는지를 확인하여 반환한다.
     * </p>
     * <p>
     *     만약 from과 to에 대응되는 Vertex가 존재하지 않을 경우 false를 반환한다.
     * </p>
     * @param from 시작점
     * @param to 끝점
     * @return from과 to에 대응되는 Vertex와 Edge가 존재하면 true, 그렇지 않다면 false를 반환한다.
     */
    boolean containsEdge(char from, char to);

    /**
     * <p>
     *     c에 대응되는 Vertex가 가진 Edge의 개수를 반환한다.
     *     Vertex가 가진 Edge는 이 Vertex를 시점으로 하는 Edge를 의미한다.
     * </p>
     * @param c Vertex의 character
     * @return Edge의 개수
     * @throws NoSuchElementException c에 대응되는 Vertex가 없을 경우.
     */
    int edgeCount(char c);
    /**
     * <p>
     *     from에 대응되는 Vertex를 시점, to에 대응되는 Vertex를 종점으로 하는
     *     Edge의 개수를 반환한다.
     * </p>
     * @param from 시작점
     * @param to 끝점
     * @return Edge의 개수
     * @throws NoSuchElementException from과 to에 대응되는 Vertex가 없을 경우.
     */
    int edgeCount(char from, char to);

    /**
     * Vertex의 개수를 반환한다.
     * @return vertex의 개수
     */
    int vertexSize();



}