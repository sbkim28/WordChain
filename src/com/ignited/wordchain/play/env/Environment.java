package com.ignited.wordchain.play.env;

import java.util.*;

public abstract class Environment {

    protected Set<String> used;
    protected char[] state;
    protected Set<String> words;

    public Environment(Set<String> words) {
        this.words = words;
        this.used = new HashSet<>();
    }

    public abstract ActionState validateAction(String action);

    public abstract void reset();

    public abstract Result step(String action);


    public static class Result {

        private char[] state;
        private boolean done;
        private float reward;

        public Result(char[] state, boolean done, float reward) {
            this.state = state;
            this.done = done;
            this.reward = reward;
        }

        public char[] getState() {
            return state;
        }

        public boolean isDone() {
            return done;
        }

        public float getReward() {
            return reward;
        }
    }
}
