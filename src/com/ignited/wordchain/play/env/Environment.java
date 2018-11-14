package com.ignited.wordchain.play.env;

import java.util.Set;

public abstract class Environment {

    protected char[] state;
    protected Set<String> words;

    public Environment(Set<String> words) {
        this.words = words;
    }

    public abstract void reset();

    public abstract ActionState validateAction(String word);

    public abstract Result step(String action);

    public class Result {

        private char[] observation;
        private boolean done;

        public Result(char[] observation, boolean done) {
            this.observation = observation;
            this.done = done;
        }

        public char[] getObservation() {
            return observation;
        }

        public boolean isDone() {
            return done;
        }
    }
}
