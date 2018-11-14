package com.ignited.wordchain.play.print;

import com.ignited.wordchain.play.env.ActionState;

public interface GamePrintable {

    void failMsg(ActionState as);
    void finishMsg(String winner);
    void playerStateMsg(String player, char... chainKey);
    void playerSubmit(String sub);


}
