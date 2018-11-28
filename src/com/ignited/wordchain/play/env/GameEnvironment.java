package com.ignited.wordchain.play.env;

import com.ignited.wordchain.util.KoreanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

public class GameEnvironment extends Environment {

    protected KeywordAt at;

    public GameEnvironment(Set<String> words) {
        this(words, new DefaultKeywordAt());
    }

    public GameEnvironment(Set<String> words, KeywordAt at) {
        super(words);
        this.at = at;
    }

    @Override
    public void reset() {
        state = 0;
        used.clear();
    }

    @Override
    public ActionState validateAction(String action) {
        ActionState ret;
        if (surrender(action)) {
            ret = ActionState.SUCCESS;
        }else if(action.length() < 2){
            ret = ActionState.TOO_SHORT;
        }else if(!match(action)) {
            ret = ActionState.UNMATCHING_KEY;
        }else if(!Pattern.matches("^[ㄱ-ㅎ가-힣]*$", action)){
            ret = ActionState.INVALID_LETTERS;
        }else if(!words.contains(action)){
            ret = ActionState.UNKNOWN_WORD;
        }else if(used.contains(action)){
            ret = ActionState.USED_WORD;
        }else {
            ret = ActionState.SUCCESS;
        }
        return ret;
    }

    @Override
    public Result step(String action) {
        setState(action);
        used.add(action);
        boolean sur = surrender(action);


        return new Result(state, sur, sur ? 1 : 0);
    }

    private boolean surrender(String action){
        return action.equalsIgnoreCase("gg") || action.equals("ㅎㅎ") || action.equals("ㅈㅈ");
    }

    protected boolean match(String action){
        return at.keywordGet(action) == state || state == 0;
    }

    protected void setState(String action){
        state = at.keywordSet(action);
    }

    public String getState(){
        return String.valueOf(state);
    }

    protected boolean isROT(){
        return false;
    }

    public class Informer {

        private Random random;

        public Informer() {
            random = new Random();
        }

        public List<String> getActions(char state){
            List<String> actions = new ArrayList<>();
            for(String word : words){
                char first = at.keywordGet(word);
                if(first == state || (isROT() && KoreanUtil.ruleOfThumb(state) == first)){
                    if(!used.contains(word)){
                        actions.add(word);
                    }
                }
            }
            return actions;
        }

        public String getRandomAction(){
            List<String> actions = getActions(state);
            String ret;
            if(actions.size() == 0){
                ret = "gg";
            }else {
                ret = actions.get(random.nextInt(actions.size()));
            }
            return ret;
        }
    }
}
