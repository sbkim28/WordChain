package com.ignited.wordchain.play.env;

import com.ignited.wordchain.util.KoreanUtil;

import java.util.Set;

public class FirstEnvironment extends ROTEnvironment {

    public FirstEnvironment(Set<String> words) {
        super(words);
    }

    @Override
    protected boolean match(String action) {
        return super.match(action.substring(action.length() - 1));
    }

    @Override
    protected void setState(String action) {
        state[0] = action.charAt(0);
        char rot = KoreanUtil.ruleOfThumb(state[0]);
        state[1] = state[0] == rot ? 0 : rot;
    }
}
