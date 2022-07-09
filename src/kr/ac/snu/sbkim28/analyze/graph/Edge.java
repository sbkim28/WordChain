package kr.ac.snu.sbkim28.analyze.graph;

public class Edge {
    private EdgeSpec spec;
    private char initialChar;
    private char finalChar;
    private EdgedVertex initialVertex;
    private EdgedVertex finalVertex;

    public Edge(String word, EdgedVertex initialVertex, EdgedVertex finalVertex) {
        this(new EdgeSpec(word), initialVertex, finalVertex);
    }

    public Edge(EdgeSpec spec, EdgedVertex initialVertex, EdgedVertex finalVertex) {
        String word = spec.getWord();
        initialChar = initialVertex.getVertexChar();
        finalChar = finalVertex.getVertexChar();
        if(word.charAt(0) != initialChar ||
        word.charAt(word.length() - 1) != finalChar){
            throw new IllegalArgumentException("String not matches to Vertex");
        }
        this.spec = spec;
        this.initialVertex = initialVertex;
        this.finalVertex = finalVertex;
    }

    public String getWord() {
        return spec.getWord();
    }

    public EdgeSpec getSpec() {
        return spec;
    }

    public char getInitialChar() {
        return initialChar;
    }

    public char getFinalChar() {
        return finalChar;
    }

    public EdgedVertex getInitialVertex() {
        return initialVertex;
    }

    public EdgedVertex getFinalVertex() {
        return finalVertex;
    }

    @Override
    public String toString() {
        return "Edge[" + spec.toString() + ']';
    }
}