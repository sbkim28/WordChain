package com.ignited.wordchain;
import com.ignited.skld.SKLDDataHandler;
import com.ignited.wordchain.data.DataManager;
import com.ignited.wordchain.play.GameManager;
import com.ignited.wordchain.play.User;
import com.ignited.wordchain.play.ai.WordSetAnalyzer;
import com.ignited.wordchain.play.env.FirstEnvironment;
import com.ignited.wordchain.play.env.GameEnvironment;
import com.ignited.wordchain.play.print.SystemPrinter;
import com.ignited.wordchain.word.KoreanWordFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class WordChain {
    public static void main(String args[]){
    /*    Set<String> wSet = new KoreanWordFilter(true,true,true,true, KoreanWordFilter.WordClass.NOUN,
                KoreanWordFilter.WordClass.PRONOUN, KoreanWordFilter.WordClass.NUMERAL, KoreanWordFilter.WordClass.BOUND_NOUN)
                .filter(SKLDDataHandler.load(DataManager.getDataAsStream("WordList.json")));
*/

        Set<String> wSet = new KoreanWordFilter(false, false, false, true, KoreanWordFilter.WordClass.NOUN)
                .filter(SKLDDataHandler.load(DataManager.getDataAsStream("WordList.json")));

        //analyze(wSet);

        GameEnvironment environment = new FirstEnvironment(wSet);
        GameManager p = new GameManager(environment);
        p.getPlayers().add(new User("USER1"));
        p.getPlayers().add(new User("USER2"));
        p.setPrintable(new SystemPrinter());
        System.out.println("게임 로딩 완료");
        p.play();
    }
    


}
