package com.ignited.wordchain.play.env;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class GameEnvironment extends Environment {

    protected Set<String> used;

    public GameEnvironment(Set<String> words) {
        super(words);
        used = new HashSet<>();
        state = new char[1];
    }

    @Override
    public void reset() {
        used.clear();
    }

    @Override
    public ActionState validateAction(String action) {
        ActionState ret;
        if (surrender(action)) {
            ret = ActionState.SUCCESS;
        }else if(!match(action)) {
            ret = ActionState.UNMATCHING_KEY;
        }else if(action.length() < 2){
            ret = ActionState.TOO_SHORT;
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
        return new Result(state, surrender(action));
    }

    private boolean surrender(String action){
        return action.equalsIgnoreCase("gg") || action.equals("ㅎㅎ") || action.equals("ㅈㅈ");
    }

    protected boolean match(String action){
        return action.charAt(0) == state[0] || state[0] == 0;
    }

    protected void setState(String action){
        state[0] = action.charAt(action.length() - 1);
    }
}
