package com.ignited.wordchain.play.ai;

import com.ignited.wordchain.play.GameManager;
import com.ignited.wordchain.play.Player;

import java.util.*;
import java.util.stream.Stream;

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

        int p;
        if(chainKey[1].isEmpty()) {
            p = wordMap.get(chainKey[0]).getValue();
            words = wordMap.get(chainKey[0]).getWords();
        }else {
            words = new HashMap<>();
            p = wordMap.get(chainKey[0]).getValue();
            words.putAll(wordMap.get(chainKey[0]).getWords());
            words.putAll(wordMap.get(chainKey[1]).getWords());
        }
        Set<String> ws = new HashSet<>();
        for (String key : words.keySet()) {
            if (av.contains(key)) {
                ws.add(key);
            }
        }

        Random r = new Random();

        String ret = null;

        if(p != 0 && p % 2 == 0){
            Optional<String> str = ws.stream().filter(s -> words.get(s) % 2 == 1)
                    .min(Comparator.comparingInt(words::get));
            if(str.isPresent()) ret = str.get();
            else p = 0;
        }
        if(p == 0){
            List<String> list = new ArrayList<>();
            for (String s : ws){
                if(words.get(s) == 0) list.add(s);
            }
            int size;
            if((size = list.size()) != 0) ret =list.get(r.nextInt(size));
        }
        if(ret == null){
            List<String> list= new ArrayList<>(ws);
            ret = list.get(r.nextInt(list.size()));
        }
        return ret;
    }

    @Override
    public void setManager(GameManager gm) {
        this.gm = gm;

    }
}
