package kr.ac.snu.sbkim28.data;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * 단어를 읽어옴. 해당 인터페이스로 단어를 하나씩 또는 한번에 읽어올 수 있음.
 *
 * @author sbkim28
 * @version 1.0
 * @see kr.ac.snu.sbkim28.data.korean.KoreanWordReader
 * @see kr.ac.snu.sbkim28.data.korean.JsonKoreanWordReader
 */
public interface IWordReader extends Iterator<IWord> {

    /**
     * 모든 단어를 한번에 읽어옴.
     * @return 리스트를 반환함.
     */
    List<? extends IWord> readAll();

    /**
     * 더 읽을 단어가 있는지 확인
     */
    @Override
    boolean hasNext();

    /**
     * 다음 단어를 읽어옴.
     * @throws java.util.NoSuchElementException
     * 더 읽을 단어가 없는 경우
     */
    @Override
    IWord next();
}
