package com.ignited.wordchain.play;

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
    public String submitWord(String... chainKey) {
        List<String> av = manager.getWordList().stream().filter(s -> {
            boolean flag = false;
            for(String key : chainKey){
                if(key.isEmpty()) continue;
                flag = s.startsWith(key);
            }
            return flag&& s.length() >= 2 && !manager.getUsed().contains(s);
        }).collect(Collectors.toList());
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
