package kr.ac.snu.sbkim28.data.korean;

import kr.ac.snu.sbkim28.data.IWordReader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 한국어 단어를 읽어오는 클래스.
 *
 * @author sbkim28
 * @version 1.0
 * @see IWordReader
 * @see JsonKoreanWordReader
 */
public abstract class KoreanWordReader implements IWordReader {

    /**
     * 단어 resoure가 위치해 있는 곳.
     */
    public final String path;

    /**
     * 생성자.
     * @param path 단어 resource의 경로.
     * @throws IOException initialize에 실패하는 경우 발생함.
     */
    public KoreanWordReader(String path) throws IOException {
        this.path = path;
        initialize();
    }


    public abstract void initialize() throws IOException;

    @Override
    public List<KoreanWord> readAll(){
        List<KoreanWord> wordList = new LinkedList<>();
        while (hasNext()) {
            KoreanWord kw = next();
            if(kw != null)
                wordList.add(next());
        }
        return wordList;
    }

    @Override
    public abstract boolean hasNext();

    /**
     * 다음 한국어 단어를 읽어옴.
     * @throws java.util.NoSuchElementException
     * 더 읽을 단어가 없는 경우
     */
    @Override
    public abstract KoreanWord next();
}
