package com.ignited.wordchain.play.env;

import com.ignited.wordchain.util.KoreanUtil;

import java.util.Map;
import java.util.Set;

public class ROTEnvironment extends GameEnvironment {

    public ROTEnvironment(Set<String> words) {
        super(words);
    }

    public ROTEnvironment(Set<String> words, KeywordAt at) {
        super(words, at);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    protected boolean match(String action) {
        char rot = KoreanUtil.ruleOfThumb(state);
        return super.match(action) || rot == at.keywordGet(action);

    }

    @Override
    public String getState() {
        char rot = KoreanUtil.ruleOfThumb(state);
        if(rot == state) {
            return super.getState();
        }else {
            return state + "(" + rot + ")";
        }
    }

    @Override
    protected boolean isROT() {
        return true;
    }


}
