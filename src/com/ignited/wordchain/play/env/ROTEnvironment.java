package com.ignited.wordchain.play.env;

import com.ignited.wordchain.util.KoreanUtil;

import java.util.Map;
import java.util.Set;

public class ROTEnvironment extends GameEnvironment {

    public ROTEnvironment(Set<String> words) {
        super(words);
        state = new char[2];
    }

    public ROTEnvironment(Set<String> words, KeywordAt at) {
        super(words, at);
        state = new char[2];
    }

    @Override
    public void reset() {
        super.reset();
        state = new char[2];
    }

    @Override
    protected boolean match(String action) {
        if(state[1] == 0){
            return super.match(action);
        }else {
            return super.match(action) || state[1] == action.charAt(at.keywordGet(action));
        }
    }

    @Override
    protected void setState(String action) {
        super.setState(action);
        char rot = KoreanUtil.ruleOfThumb(state[0]);
        state[1] = state[0] == rot ? 0 : rot;
    }

    public class ROTInformer extends Informer {

        public ROTInformer() { }

        @Override
        public void initializeTable(Map<Character, Map<String, Float>> table) {
            super.initializeTable(table);
            for(Character key : table.keySet()){
                char rotKey = KoreanUtil.ruleOfThumb(key);
                if(rotKey != key && table.containsKey(rotKey)){
                    table.get(key).putAll(table.get(rotKey));
                }
            }
        }
    }
}
