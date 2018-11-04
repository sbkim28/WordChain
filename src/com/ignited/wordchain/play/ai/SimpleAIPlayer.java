package com.ignited.wordchain.play.ai;

import com.ignited.wordchain.play.GameManager;
import com.ignited.wordchain.play.Player;

import java.util.Collections;
import java.util.List;

public class SimpleAIPlayer extends Player {

    private GameManager manager;


    public SimpleAIPlayer(String name, GameManager manager) {
        super(name);
        this.manager = manager;
    }

    @Override
    public String submitWord(String... chainKey) {
        List<String> av = manager.usableWord(true);
        String ret;
        if(av.size() == 0){
            ret =  "gg";
        } else {
            Collections.shuffle(av);
            ret =  av.get(0);
        }

        log(chainKey);
        System.out.println(ret);

        return ret;
    }
}
