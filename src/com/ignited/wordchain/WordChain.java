package com.ignited.wordchain;
import com.ignited.wordchain.list.RuleSetter;
import com.ignited.wordchain.play.GameManager;
import com.ignited.wordchain.play.User;
import com.ignited.wordchain.play.ai.SimpleAIPlayer;

import java.util.Set;

public class WordChain {
    public static void main(String args[]){
        RuleSetter rs = new RuleSetter();
        Set<String> wSet = rs.getWords("WordList.json");
        GameManager p = new GameManager(wSet);
        p.getPlayers().add(new User("USER"));
        p.getPlayers().add(new SimpleAIPlayer("AI", p));
        System.out.println("게임 로딩 완료");
        p.play();
    }




}
