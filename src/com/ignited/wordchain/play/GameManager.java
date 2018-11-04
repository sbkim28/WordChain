package com.ignited.wordchain.play;

import com.ignited.wordchain.util.KoreanUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GameManager {

    private final String NAME = "System";

    private final Set<String> wordList;
    private final List<Player> players;

    private final Set<String> used;
    private int turn;
    private String[] chainKey;

    private boolean ruleOfThumb = true;

    public GameManager(Set<String> wordList) {
        this(wordList, new ArrayList<>());
    }

    public GameManager(Set<String> wordList, List<Player> players) {
        this.wordList = wordList;
        this.players = players;
        chainKey = new String[]{"",""};
        used = new HashSet<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setRuleOfThumb(boolean ruleOfThumb) {
        this.ruleOfThumb = ruleOfThumb;
    }

    public void play(){
        boolean flag = true;
        while (flag){
            flag = playGames();
        }
        chainKey[0] = "";
        chainKey[1] = "";
    }

    private boolean playGames(){
        int i = getTurn();
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String str = players.get(i).submitWord(chainKey);
            if (str.equalsIgnoreCase("gg") || str.equals("ㅎㅎ")) {
                return false;
            }

            if (chainKey[0].isEmpty() || matchThumb(str)) {

                if (!validate(str)) continue;

                setChainKey(str);
                used.add(str);
            } else {
                System.out.println(addName("첫글자가 일치하지 않습니다"));
                continue;
            }


            return true;
        }
    }

    private void setChainKey(String str){
        chainKey[0] = str.substring(str.length() - 1);
        String rot = (KoreanUtil.ruleOfThumb(chainKey[0]));
        chainKey[1] = chainKey[0].equals(rot) || !ruleOfThumb ? "" : rot;
    }

    private boolean matchThumb(String str){
        boolean flag = false;
        for (String s : chainKey){
            if(s.isEmpty()) continue;
            flag = flag || str.startsWith(s);
        }
        return flag;
    }

    private boolean validate(String word){
        boolean flag = false;
        if(word.length() < 2){
            System.out.println(addName("단어의 길이가 너무 짧습니다."));
        } else if (!Pattern.matches("^[ㄱ-ㅎ가-힣]*$", word)){
            System.out.println(addName("올바르지 않은 문자입니다."));
        } else if( !wordList.contains(word)){
            System.out.println(addName("사전에 없는 단어입니다."));
        } else if (used.contains(word)){
            System.out.println(addName("이미 사용한 단어입니다"));
        }else {
            flag = true;
        }

        return flag;
    }

    private String addName(String str)
    {
        return NAME + ":" + str;
    }
    private int getTurn(){
        return turn++ % players.size();
    }

    public List<String> usableWord(boolean checkUsed){
        return wordList.stream().filter(s -> {
            boolean flag = false;
            for(String key : chainKey){
                if(key.isEmpty()) continue;
                flag = s.startsWith(key);
            }
            return flag && (!used.contains(s) || !checkUsed);
        }).collect(Collectors.toList());
    }
}
