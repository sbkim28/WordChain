package com.ignited.wordchain;

import com.ignited.skld.KoreanWord;
import com.ignited.skld.Property;
import com.ignited.skld.SKLDDataHandler;
import com.ignited.wordchain.data.DataManager;
import com.ignited.wordchain.play.GameManager;
import com.ignited.wordchain.play.SimpleAIPlayer;
import com.ignited.wordchain.play.User;

import java.util.ArrayList;
import java.util.List;

public class WordChain {
    public static void main(String args[]){
        List<KoreanWord> kws = SKLDDataHandler.load(DataManager.getDataAsStream("WordList.json"));

        List<String> wlist = new ArrayList<>();
        for (KoreanWord kw :kws ){
            if(kw.getHomonym() == 1 && !kw.isContainsOldHangeul()){
                for (Property pt : kw.getProperties()){
                    if(pt.getWordClass().contains("명사")){
                        List<String> attr = pt.getAttributes();
                        if(!attr.contains("북한어")
                            && !attr.contains("방언") && !attr.contains("옛말")) {
                            wlist.add(kw.getWord());
                            break;
                        }
                    }
                }
            }
        }
        GameManager p = new GameManager(wlist);
        p.getPlayers().add(new User("USER"));
        p.getPlayers().add(new SimpleAIPlayer("AI", p));
        System.out.println("게임 로딩 완료");
        p.play();
    }


}
