package com.ignited.wordchain.play.env;

import com.ignited.wordchain.util.KoreanUtil;

import java.util.HashMap;
import java.util.Map;
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
        state = new char[1];
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
        boolean sur = surrender(action);
        return new Result(state, sur, sur ? 5 : 0);
    }

    private boolean surrender(String action){
        return action.equalsIgnoreCase("gg") || action.equals("ㅎㅎ") || action.equals("ㅈㅈ");
    }

    protected boolean match(String action){
        return action.charAt(at.keywordGet(action)) == state[0] || state[0] == 0;
    }

    protected void setState(String action){
        state[0] = action.charAt(at.keywordSet(action));
    }

    public class Informer {

        protected Map<Character, Map<String, Float>> table;
        private Random random;

        public Informer() {
            table = new HashMap<>();
            initializeTable(table);
        }

        public void initializeTable(Map<Character, Map<String, Float>> table){
            for(String word : words){
                char start = word.charAt(0);
                char end = word.charAt(word.length() - 1);

                if(!table.containsKey(start)){
                    table.put(start, new HashMap<>());
                }
                if(!table.containsKey(end)){
                    table.put(end, new HashMap<>());
                }
                table.get(start).put(word, 0f);
            }
            if(state.length == 2) {
                initializeROTTable(table);
            }
        }

        private void initializeROTTable(Map<Character, Map<String, Float>> table) {
            for(Character key : table.keySet()){
                char rotKey = KoreanUtil.ruleOfThumb(key);
                if(rotKey != key && table.containsKey(rotKey)){
                    table.get(key).putAll(table.get(rotKey));
                }
            }
        }

        public String sample(){

            if(state[0] == 0){
                // tmp;
                return "가축";
            }
            String[] words = table.get(state[0])
                    .keySet().stream()
                    .filter(s -> !used.contains(s))
                    .toArray(String[]::new);

            if(words.length == 0) return "gg";
            else {
                if (random == null) {
                    random = new Random();
                }
                return words[random.nextInt(words.length)];
            }
        }
    }
}
