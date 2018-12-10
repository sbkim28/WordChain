package com.ignited.wordchain;
import com.ignited.skld.SKLDDataHandler;
import com.ignited.wordchain.data.DataManager;
import com.ignited.wordchain.play.WordSetAnalyzer;
import com.ignited.wordchain.play.env.DefaultKeywordAt;
import com.ignited.wordchain.play.GameManager;
import com.ignited.wordchain.play.user.User;
import com.ignited.wordchain.play.env.GameEnvironment;
import com.ignited.wordchain.play.env.ROTEnvironment;
import com.ignited.wordchain.play.print.SystemPrinter;
import com.ignited.wordchain.play.user.ai.RandomAI;
import com.ignited.wordchain.word.KoreanWordFilter;

import java.util.*;

public class WordChain {
    public static void main(String args[]){
    /*    Set<String> wSet = new KoreanWordFilter(true,true,true,true, KoreanWordFilter.WordClass.NOUN,
                KoreanWordFilter.WordClass.PRONOUN, KoreanWordFilter.WordClass.NUMERAL, KoreanWordFilter.WordClass.BOUND_NOUN)
                .filter(SKLDDataHandler.load(DataManager.getDataAsStream("WordList.json")));
*/

        Set<String> wSet = new KoreanWordFilter(false, false, false, false, KoreanWordFilter.WordClass.NOUN)
                .filter(SKLDDataHandler.load(DataManager.getDataAsStream("WordList.json")));

        GameEnvironment environment = new ROTEnvironment(wSet, new DefaultKeywordAt());

        GameManager p = new GameManager(environment, new User("USER1"), new RandomAI("AI", environment.new Informer()));
        p.setPrintable(new SystemPrinter());
        System.out.println("게임 로딩 완료");
        p.play();

    }




}
