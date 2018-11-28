package com.ignited.wordchain.play.user.ai;

//todo

import com.ignited.wordchain.play.env.Environment;
import com.ignited.wordchain.play.env.GameEnvironment;
import com.ignited.wordchain.play.user.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AI extends Player implements ResultGettable{

    private GameEnvironment.Informer informer;
    private Map<Character, Map<String, Float>> wordMap;

    private Environment.Result r;
    private Random random;


    public AI(String name, GameEnvironment.Informer informer) {
        super(name, true);
        this.informer = informer;
        wordMap = new HashMap<>();
        random = new Random();
    }

    private final double e = 1;

    @Override
    public String submitWord() {
        if(r == null) return "가축"; // default

        char state = r.getState();


        String action;

        if(random.nextDouble() < e){
            action = informer.getRandomAction();
        }else {
            List<String> actions = informer.getActions(state);
            if(actions.size() == 0) action = "gg";
            else{
                action = selectMax(state, actions);
            }
        }


        return action;
    }

    private String selectMax(char state, List<String> actions){
        return actions.get(0);
    }

    @Override
    public void get(Environment.Result result) {
        r = result;

    }


}

