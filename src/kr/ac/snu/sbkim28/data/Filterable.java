package kr.ac.snu.sbkim28.data;

public interface Filterable<T extends IWord> {
    boolean filter(T word);
}
