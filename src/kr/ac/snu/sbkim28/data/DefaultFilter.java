package kr.ac.snu.sbkim28.data;

public class DefaultFilter<T extends  IWord> implements Filterable<T  >{
    @Override
    public boolean filter(T word) {
        return true;
    }
}
