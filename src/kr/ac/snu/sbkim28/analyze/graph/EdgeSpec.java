package kr.ac.snu.sbkim28.analyze.graph;

public class EdgeSpec {
    private final String word;

    public EdgeSpec(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return word;
    }
}
