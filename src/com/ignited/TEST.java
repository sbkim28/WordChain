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
        /*
         할 - 팽할 - 할팽 - 교할 - 흰진교

         낱 죽 2
         됨 죽 2
         뽀 죽 2
         헤 죽음 4
         협 죽 4
         은 죽 4
         흉 죽 4
         렘 죽 4
         참 죽 4
         엠 죽 4
         잡 죽 6
         삐 죽 4
         톰 죽 4
         펙 한 5
         씀 한 5
         켈 한 5
         츨 한 5
         옴 한 5
         녜 한 5
         슛 한 5
         삶 한 5
         짬 한 5
         룽 한 5
         밋 죽 6
         빈 죽 6
         팡 죽 6
         훔 죽 6
         춘 죽 6
         쯔 죽 6
         훼 한 7 ~훼 폄훼 훼예포폄 참훼 할참
         굉 죽 8
         괴 죽 8
         멸 죽 8
         척 죽 8
         죄 죽 8
         팍 한 9
         확 한 9
         델 한 9
         푼 죽 10
         팽 죽 10
         견 죽 10
         샬 한 11

         훤 한 11
         쟁 죽 12
          */
        char[] dead = new char[]{'뽀', '됨', '낱', '엠', '삐', '톰','잡', '헤', '협', '은', '흉', '렘', '참',  '밋', '빈', '팡', '훔', '춘', '쯔', '굉', '괴', '멸', '척', '죄', '푼', '팽', '견', '쟁' };
        Map<Character, WordSetAnalyzer.TableRow> wordMap = new HashMap<>();
        WordSetAnalyzer.initFirstMap(wordMap, wSet, true);
        WordSetAnalyzer.createWordMap(wordMap, new KeywordAt() {
            @Override
            public int keywordSet(String word) {
                return 0;
            }

            @Override
            public int keywordGet(String word) {
                return word.length() - 1;
            }
        });

        Map<Character, Set<String>> undyingCells = new HashMap<>();
        l : for (Character tkey : wordMap.keySet()){
            WordSetAnalyzer.TableRow tr = wordMap.get(tkey);
            //for (char c : dead){
            //   if(c == tkey) continue l;
            //}
            if(tr.getKillingIndex() == 0) {
                Set<String> undyingWords = new HashSet<>();
                label : for (String words : tr.getWords().keySet()){
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
