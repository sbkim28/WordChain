package com.ignited.wordchain.play.env;

import com.ignited.wordchain.util.KoreanUtil;

import java.util.Set;

public class ROTEnvironment extends GameEnvironment {
    public ROTEnvironment(Set<String> words) {
        super(words);
        state = new char[2];
    }

    @Override
    protected boolean match(String action) {
        if(state[1] == 0){
            return super.match(action);
        }else {
            return super.match(action) || state[1] == action.charAt(0);
        }
    }

    @Override
    protected void setState(String action) {
        super.setState(action);
        char rot = KoreanUtil.ruleOfThumb(state[0]);
        state[1] = state[0] == rot ? 0 : rot;
    }
}
