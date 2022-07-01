package kr.ac.snu.sbkim28.data.korean;

import kr.ac.snu.sbkim28.data.DefaultFilter;
import kr.ac.snu.sbkim28.data.Filterable;
import kr.ac.snu.sbkim28.data.IWordSelector;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 한국어 단어를 선별하여 가져옴.
 *
 * @author sbkim28
 * @see IWordSelector
 * @see Filterable
 */
public class KoreanWordSelector implements IWordSelector {
    /**
     * 한국어 단어를 읽어오는 객체
     */
    private KoreanWordReader kwReader;
    /**
     * 한국어 단어를 필터링할 수 있는 메써드를 제공하는 객체
     */
    private Filterable<KoreanWord> filter;

    /**
     * {@link KoreanWordReader#next()}에서
     * 문제가 생겨서 다음의 단어를 읽어오지 못하고
     * null을 반환하였을 때, 이를 무시하고 예외를 발생시키지 않도록 함.
     * default false
     */
    public boolean ignoreException = false;

    /**
     * 생성자.
     * {@link KoreanWordSelector#filter}을 {@link DefaultFilter}로 설정함.
     * @see DefaultFilter
     * @param kwReader 한국어 단어를 읽어올 수 있는 객체.
     */
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
