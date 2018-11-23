package com.ignited.wordchain.play.user.ai;

import com.ignited.wordchain.play.user.Player;
import com.ignited.wordchain.play.env.GameEnvironment;

public class RandomAI extends Player {

    private GameEnvironment.Informer informer;

    public RandomAI(String name, GameEnvironment.Informer informer) {
        super(name, true);
        this.informer = informer;
    }

    @Override
    public String submitWord() {
        return informer.sample();
    }
}
