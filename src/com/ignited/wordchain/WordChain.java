package com.ignited.wordchain;
import com.ignited.wordchain.play.GameManager;
import com.ignited.wordchain.play.User;
import com.ignited.wordchain.play.ai.IntermediateAIPlayer;
import com.ignited.wordchain.play.ai.SimpleAIPlayer;
import com.ignited.wordchain.play.ai.WordSetAnalyzer;
import com.ignited.wordchain.play.print.SystemPrinter;
import com.ignited.wordchain.word.KoreanWordFilter;
import com.ignited.wordchain.word.RuleSetter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordChain {
    public static void main(String args[]){
        /*RuleSetter rs = new RuleSetter(true,true,true,true
        , RuleSetter.WordClass.NOUN, RuleSetter.WordClass.PRONOUN, RuleSetter.WordClass.NUMERAL);*/

        //RuleSetter rs = new RuleSetter("WordList.json", new KoreanWordFilter(false,false,false,false, KoreanWordFilter.WordClass.NOUN));

        RuleSetter rs = new RuleSetter("WordList.json", new KoreanWordFilter());

        //RuleSetter rs = new RuleSetter("WordList.json", new KoreanWordFilter(true,true,true,true, KoreanWordFilter.WordClass.NOUN));

        Set<String> wSet = rs.getWords();


        GameManager p = new GameManager(wSet);
        p.getPlayers().add(new User("USER"));
        p.getPlayers().add(new IntermediateAIPlayer("AI", wSet, true));
        p.setPrintable(new SystemPrinter());
        System.out.println("게임 로딩 완료");
        p.play();

    }




}
