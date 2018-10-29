package com.ignited.wordchain.play;

import org.jsoup.select.Collector;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleAIPlayer extends Player {

    private GameManager manager;

    public SimpleAIPlayer(String name, GameManager manager) {
        super(name);
        this.manager = manager;
    }

    @Override
    public String submitWord(String chainKey) {
        List<String> av = manager.getWordList().stream().filter(s -> s.startsWith(chainKey) && s.length() >= 2 && !manager.getUsed().contains(s)).collect(Collectors.toList());
        String ret;
        if(av.size() == 0){
            ret =  "gg";
        } else {
            Collections.shuffle(av);
            ret =  av.get(0);
        }
        System.out.println(getName() + ":" + chainKey + "> " + ret);
        return ret;
    }
}
