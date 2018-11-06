package com.ignited.wordchain.play;

import com.ignited.wordchain.play.ai.ManagerSettable;
import com.ignited.wordchain.play.print.GamePrintable;
import com.ignited.wordchain.util.KoreanUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GameManager {

    private final Set<String> wordList;
    private final List<Player> players;
    private GamePrintable printable;


    private final Set<String> used;
    private int turn;
    private String[] chainKey;

    private boolean ruleOfThumb = true;

    public GameManager(Set<String> wordList) {
        this(wordList, new ArrayList<>());
    }

    public GameManager(Set<String> wordList, List<Player> players) {
        this(wordList, players, new GamePrintable() {
            @Override public void failMsg(FailType ft) { }
            @Override public void finishMsg(String winner) { }
            @Override public void playerStateMsg(String player, String... chainKey) { }
            @Override public void playerSubmit(String sub) { }
        });
    }

    public GameManager(Set<String> wordList, List<Player> players, GamePrintable printable) {
        this.wordList = wordList;
        this.players = players;
        this.printable = printable;
        chainKey = new String[]{"",""};
        used = new HashSet<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPrintable(GamePrintable printable) {
        this.printable = printable;
    }

    public void setRuleOfThumb(boolean ruleOfThumb) {
        this.ruleOfThumb = ruleOfThumb;
    }

    public boolean isRuleOfThumb() {
        return ruleOfThumb;
    }

    public void play(){
        boolean flag = true;
        for(Player p : players){
            if(p instanceof ManagerSettable){
                ((ManagerSettable) p).setManager(this);
            }
        }
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
                printable.failMsg(GamePrintable.FailType.UNKNOWN);
            }
            Player p = players.get(i);
            printable.playerStateMsg(p.getName(), chainKey);
            String str = p.submitWord(chainKey);
            if(p.isPrintSubmit()) printable.playerSubmit(str);
            if (str.equalsIgnoreCase("gg") || str.equals("ㅎㅎ")) {
                return false;
            }

            if (chainKey[0].isEmpty() || matchThumb(str)) {

                if (!validate(str)) continue;

                setChainKey(str);
                used.add(str);
            } else {
                printable.failMsg(GamePrintable.FailType.UNMATCHING_KEY);
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
            printable.failMsg(GamePrintable.FailType.TOO_SHORT);
        } else if (!Pattern.matches("^[ㄱ-ㅎ가-힣]*$", word)){
            printable.failMsg(GamePrintable.FailType.INVALID_LETTERS);
        } else if( !wordList.contains(word)){
            printable.failMsg(GamePrintable.FailType.UNKNOWN_WORD);
        } else if (used.contains(word)){
            printable.failMsg(GamePrintable.FailType.USED_WORD);
        }else {
            flag = true;
        }

        return flag;
    }

    private int getTurn(){
        return turn++ % players.size();
    }

    public Set<String> usableWord(boolean checkUsed){
        return wordList.stream().filter(s -> {
            boolean flag = false;
            for(String key : chainKey){
                if(key.isEmpty()) continue;
                flag = s.startsWith(key);
            }
            return flag && (!used.contains(s) || !checkUsed);
        }).collect(Collectors.toSet());
    }
}
