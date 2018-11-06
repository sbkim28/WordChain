package com.ignited.wordchain.play.ai;

import com.ignited.wordchain.play.GameManager;
import com.ignited.wordchain.play.Player;

import java.util.*;

public class IntermediateAIPlayer extends Player implements ManagerSettable {

    private GameManager gm;
    private Map<String, WordSetAnalyzer.TableRow> wordMap;

    public IntermediateAIPlayer(String name, Collection<String> words, boolean ruleOfThumb) {
        super(name);
        wordMap = ruleOfThumb ? WordSetAnalyzer.analyzeRuleOfThumb(words) : WordSetAnalyzer.analyze(words);
    }



    @Override
    public String submitWord(String... chainKey) {
        Set<String> av = gm.usableWord(true);

        String ret;
        if(av.size() == 0){
            ret =  "gg";
        } else {
            ret = selectWord(av, chainKey);
        }

        return ret;
    }

    private String selectWord(Set<String> av, String... chainKey){

        Map<String, Integer> words;

        if(chainKey[1].isEmpty()) {
            words = wordMap.get(chainKey[0]).getWords();
        }else {
            words = new HashMap<>();
            words.putAll(wordMap.get(chainKey[0]).getWords());
            words.putAll(wordMap.get(chainKey[1]).getWords());
        }

        String ret = "";
        int val = -1;
        for (String key : words.keySet()){
            int keyVal = words.get(key);
            if(!av.contains(key)) continue;
            if(keyVal % 2 == 1){
                if(val == -1 || val % 2 == 0 || val > keyVal) {
                    val = keyVal;
                    ret = key;
                }
            }else if(keyVal == 0 && (val % 2 == 0 || val == -1)){
                val = 0;
                ret = key;
            }else if(keyVal % 2 == 0 && ((val != 0 && val % 2 == 0) || val == -1) && keyVal > val){
                val = keyVal;
                ret = key;
            }
        }
        return ret;
    }

    @Override
    public void setManager(GameManager gm) {
        this.gm = gm;

    }
}
