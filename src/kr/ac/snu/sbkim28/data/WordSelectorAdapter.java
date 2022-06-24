package kr.ac.snu.sbkim28.data;

import kr.ac.snu.sbkim28.data.korean.KoreanWordSelector;

import java.util.Iterator;

public class WordSelectorAdapter implements Iterable<String>, Iterator<String> {

    private KoreanWordSelector kws;
    private String buffer;

    public WordSelectorAdapter(KoreanWordSelector kws) {
        this.kws = kws;
        buffer = kws.filter();
    }

    @Override
    public boolean hasNext() {
        return buffer != null;
    }

    @Override
    public String next() {
        String _tmp = buffer;
        buffer = kws.filter();
        return _tmp;
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }
}
