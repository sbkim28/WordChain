package com.ignited;

import com.ignited.skld.SKLDDataHandler;
import com.ignited.wordchain.data.DataManager;
import com.ignited.wordchain.play.env.KeywordAt;
import com.ignited.wordchain.play.WordSetAnalyzer;
import com.ignited.wordchain.word.KoreanWordFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TEST {

    public static void main(String args[]){
        Set<String> wSet = new KoreanWordFilter(false, false, false, false, KoreanWordFilter.WordClass.NOUN)
                .filter(SKLDDataHandler.load(DataManager.getDataAsStream("WordList.json")));
        analyze(wSet);
    }

    public static void analyze(Collection<String> wSet){

        Map<Character, WordSetAnalyzer.TableRow> wordMap = new HashMap<>();
        WordSetAnalyzer.initMiddleMap(wordMap, wSet, true);
        WordSetAnalyzer.createWordMap(wordMap, new KeywordAt() {
            @Override
            public int keywordSet(String word) { return (word.length() - 1) / 2; }
            @Override
            public int keywordGet(String word) { return 0; }
        });

        Map<Character, Set<String>> undyingCells = new HashMap<>();
        for (Character tkey : wordMap.keySet()){
            WordSetAnalyzer.TableRow tr = wordMap.get(tkey);
            if(tr.getKillingIndex() == 0) {
                Set<String> undyingWords = new HashSet<>();
                for (String words : tr.getWords().keySet()){
                    if(tr.getWords().get(words) == 0){
                        undyingWords.add(words);
                    }
                }

                undyingCells.put(tkey, undyingWords);
            }
        }

        List<Character> undying = new ArrayList<>(undyingCells.keySet());
        undying.sort(Character::compareTo);
        System.out.println(undying);
        System.out.println(undyingCells);

        List<Character> clist = new ArrayList<>(wordMap.keySet());
        clist.sort(Character::compareTo);

        System.out.println("Ready.");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int i = 0;
            while(true){
                if(br.ready()){
                    String s = br.readLine();
                    if(s.isEmpty()){
                        continue;
                    }
                    if(s.startsWith("#")){
                        char c = s.charAt(1);
                        if (undyingCells.containsKey(c)) {
                            List<String> sort = new ArrayList<>(undyingCells.get(c));
                            sort.sort(String::compareTo);
                            System.out.println("UNDYING : " + sort);
                        }else if(wordMap.containsKey(c)){
                            System.out.println(wordMap.get(c));
                        }else {
                            System.out.println("Invalid.");
                        }
                        continue;
                    }else if(s.startsWith("@")){

                        int val;
                        do {
                            val = wordMap.get(clist.get(++i)).getKillingIndex() - 1;
                        } while (val == 0 || val % 2 == 0);

                        Map<String, Integer> words = wordMap.get(clist.get(i)).getWords();
                        System.out.print(clist.get(i)+"("+val + ") : ");
                        for (String key : words.keySet()){
                            if(words.get(key) == val){
                                System.out.print(key + ", ");
                            }
                        }
                        System.out.println();
                    }else if(s.startsWith("!")){
                        System.out.println("current position : " + i);
                    }else if(s.startsWith("&")) {
                        i = Integer.parseInt(s.substring(1));
                    } else {
                        char c = s.charAt(0);
                        System.out.println(clist.indexOf(c));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
