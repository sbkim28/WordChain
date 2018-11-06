package com.ignited.wordchain.play.print;

public interface GamePrintable {

    void failMsg(FailType ft);
    void finishMsg(String winner);
    void playerStateMsg(String player, String... chainKey);
    void playerSubmit(String sub);

    enum FailType {
        TOO_SHORT, INVALID_LETTERS, UNKNOWN_WORD, USED_WORD, UNMATCHING_KEY, UNKNOWN;


    }
}
