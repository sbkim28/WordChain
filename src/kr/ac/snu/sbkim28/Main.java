package kr.ac.snu.sbkim28;

import kr.ac.snu.sbkim28.data.IWordSelector;
import kr.ac.snu.sbkim28.data.korean.JsonKoreanWordReader;
import kr.ac.snu.sbkim28.data.korean.KoreanFilter;
import kr.ac.snu.sbkim28.data.korean.KoreanWordReader;
import kr.ac.snu.sbkim28.data.korean.KoreanWordSelector;
import kr.ac.snu.sbkim28.legacy.analyze.WordAnalyzer;
import kr.ac.snu.sbkim28.legacy.analyze.graph.MatrixWordGraph;
import kr.ac.snu.sbkim28.legacy.analyze.graph.WordGraph;
import kr.ac.snu.sbkim28.util.Timer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    public static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String... args) throws IOException {
        KoreanWordReader reader = new JsonKoreanWordReader("src\\res\\WordList.json");
        IWordSelector kws = new KoreanWordSelector(reader,
                new KoreanFilter());
        Set<String> wordSet = kws.getWords();
        bw.write(wordSet.toString());
        bw.flush();
    }

}
