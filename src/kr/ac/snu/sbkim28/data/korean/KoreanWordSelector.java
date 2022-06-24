package kr.ac.snu.sbkim28.data.korean;

import kr.ac.snu.sbkim28.data.DefaultFilter;
import kr.ac.snu.sbkim28.data.Filterable;
import kr.ac.snu.sbkim28.data.IWordSelector;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class KoreanWordSelector implements IWordSelector {
    private KoreanWordReader kwReader;
    private Filterable<KoreanWord> filter;

    public boolean ignoreException = false;

    public KoreanWordSelector(KoreanWordReader kwReader) {
        this(kwReader, new DefaultFilter<>());
    }

    public KoreanWordSelector(KoreanWordReader kwReader, Filterable<KoreanWord> filter) {
        assert kwReader != null && filter != null;

        this.filter = filter;
        this.kwReader = kwReader;
    }

    @Override
    public Set<String> getWords() {
        Set<String> words = new HashSet<>();
        String word;

        while ((word = filter()) != null) {
            words.add(word);
        }

        return words;
    }

    @Override
    public String filter() {
        while (kwReader.hasNext()) {
            KoreanWord kw = kwReader.next();
            if (kw == null) {
                if (!ignoreException)
                    throw new RuntimeException("Failed to read word");
            }
            else {
                if (!kw.word.isEmpty() && filter.filter(kw))
                    return kw.word;
            }
        }
        return null;
    }

    public void initialize() throws IOException {
        kwReader.initialize();
    }
}
