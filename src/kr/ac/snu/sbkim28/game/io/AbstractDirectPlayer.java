package kr.ac.snu.sbkim28.game.io;

import kr.ac.snu.sbkim28.game.core.AbstractPlayer;

public abstract class AbstractDirectPlayer extends AbstractPlayer {

    protected PlayerInputReceiver inputReceiver;
    protected GameDisplayer displayer;

    protected abstract void createInputReceiver();
    protected abstract void createDisplayer();

}
