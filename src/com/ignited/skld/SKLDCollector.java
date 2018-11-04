package com.ignited.skld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SKLDCollector
 *
 * Collects the entire words contained in the Standard Korean Language Dictionary.
 *
 * @author Ignited
 */
public class SKLDCollector {


    /**
     * The constant The final index.
     * Note : it changes continuously.
     */

    public static final int END_INDEX = 519208;

    /**
     * Collects the list of the entire words
     *
     * @return the list
     * @throws IOException when failed to collect
     */
    public static List<KoreanWord> collect() throws IOException {
        return collect(1, END_INDEX);
    }

    /**
     * Collects the list of the words.
     * From parameter start(inclusive) to parameter end(inclusive)
     *
     * @param start the start point
     * @param end   the end point
     * @return the list
     * @throws IOException when failed to collect
     */
    public static List<KoreanWord> collect(int start, int end) throws IOException{
        List<KoreanWord> wordList = new ArrayList<>();
        for(int index = start;index<= end;++index){
            KoreanWord word = SKLDReader.read(index);
            if(word != null) {
                wordList.add(word);
            }
        }
        return wordList;
    }

}
