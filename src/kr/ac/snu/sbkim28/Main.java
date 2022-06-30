package kr.ac.snu.sbkim28;

import kr.ac.snu.sbkim28.data.IWordSelector;
import kr.ac.snu.sbkim28.data.korean.JsonKoreanWordReader;
import kr.ac.snu.sbkim28.data.korean.KoreanFilter;
import kr.ac.snu.sbkim28.data.korean.KoreanWordReader;
import kr.ac.snu.sbkim28.data.korean.KoreanWordSelector;
import kr.ac.snu.sbkim28.game.core.Environment;
import kr.ac.snu.sbkim28.game.core.LocalEnvironment;
import kr.ac.snu.sbkim28.game.core.Player;
import kr.ac.snu.sbkim28.game.dp.ConsoleGameDisplayer;
import kr.ac.snu.sbkim28.game.dp.ConsolePlayerInputReceiver;
import kr.ac.snu.sbkim28.game.dp.DirectPlayer;

import java.io.*;

public class Main {
    public static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String... args) throws IOException {
        KoreanWordReader reader = new JsonKoreanWordReader("src\\res\\WordList.json");
        IWordSelector kws = new KoreanWordSelector(reader,
                new KoreanFilter());
        /*Set<String> wordSet = kws.getWords();
        bw.write(wordSet.toString());
        bw.flush();*/

        Player player1 = new DirectPlayer(new ConsolePlayerInputReceiver(br), new ConsoleGameDisplayer(bw));
        Player player2 = new DirectPlayer(new ConsolePlayerInputReceiver(br), new ConsoleGameDisplayer(bw));
        Environment environment = new LocalEnvironment(player1, player2);
        environment.setWordSet(kws.getWords());
        environment.initialize();
        environment.runGame();
    }
}
