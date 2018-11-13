package com.ignited.wordchain;
import com.ignited.skld.SKLDDataHandler;
import com.ignited.wordchain.data.DataManager;
import com.ignited.wordchain.play.GameManager;
import com.ignited.wordchain.play.User;
import com.ignited.wordchain.play.ai.IntermediateAIPlayer;
import com.ignited.wordchain.play.ai.WordSetAnalyzer;
import com.ignited.wordchain.play.print.SystemPrinter;
import com.ignited.wordchain.word.KoreanWordFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordChain {
    public static void main(String args[]){

        Set<String> wSet = new KoreanWordFilter(false,false,false,true, KoreanWordFilter.WordClass.NOUN)
                .filter(SKLDDataHandler.load(DataManager.getDataAsStream("WordList.json")));
        

        GameManager p = new GameManager(wSet);
        p.getPlayers().add(new User("USER"));
        p.getPlayers().add(new IntermediateAIPlayer("AI", wSet, true));
        p.setPrintable(new SystemPrinter());
        System.out.println("게임 로딩 완료");
        p.play();


    }




}
