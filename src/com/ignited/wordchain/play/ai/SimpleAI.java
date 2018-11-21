package com.ignited.wordchain.play.ai;

import com.ignited.wordchain.play.Player;
import com.ignited.wordchain.play.env.GameEnvironment;

public class SimpleAI extends Player {

    private GameEnvironment.Informer informer;

    public SimpleAI(String name, GameEnvironment.Informer informer) {
        super(name, true);
        this.informer = informer;
    }

    @Override
    public String submitWord() {
        return informer.sample();
    }
}
