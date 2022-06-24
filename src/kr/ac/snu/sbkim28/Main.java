package kr.ac.snu.sbkim28;

import kr.ac.snu.sbkim28.data.IWordSelector;
import kr.ac.snu.sbkim28.data.korean.JsonKoreanWordReader;
import kr.ac.snu.sbkim28.data.korean.KoreanFilter;
import kr.ac.snu.sbkim28.data.korean.KoreanWordReader;
import kr.ac.snu.sbkim28.data.korean.KoreanWordSelector;
import kr.ac.snu.sbkim28.game.core.ConsoleInputPlayer;
import kr.ac.snu.sbkim28.game.core.Environment;
import kr.ac.snu.sbkim28.game.core.Player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    public static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String... args) throws IOException {
        /*KoreanWordReader reader = new JsonKoreanWordReader("src\\res\\WordList.json");
        IWordSelector kws = new KoreanWordSelector(reader,
                new KoreanFilter());
        Set<String> wordSet = kws.getWords();
        bw.write(wordSet.toString());
        bw.flush();*/

        Player player1 = new ConsoleInputPlayer();
        Player player2 = new ConsoleInputPlayer();
        Environment environment = new Environment(player1, player2);
        environment.runGame();
    }
}
