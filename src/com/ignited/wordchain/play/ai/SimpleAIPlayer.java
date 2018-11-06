package com.ignited.wordchain.play.ai;

import com.ignited.wordchain.play.GameManager;
import com.ignited.wordchain.play.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleAIPlayer extends Player implements ManagerSettable{

    private GameManager manager;


    public SimpleAIPlayer(String name) {
        super(name);
    }

    @Override
    public void setManager(GameManager manager) {
        this.manager = manager;
    }

    @Override
    public String submitWord(String... chainKey) {
        List<String> av = new ArrayList<>(manager.usableWord(true));
        String ret;
        if(av.size() == 0){
            ret =  "gg";
        } else {
            Collections.shuffle(av);
            ret =  av.get(0);
        }

        return ret;
    }
}
