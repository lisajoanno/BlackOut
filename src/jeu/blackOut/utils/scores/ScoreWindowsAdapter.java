package jeu.blackOut.utils.scores;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jeu.blackOut.utils.Utils;
import t2s.SIVOXDevint;


public class ScoreWindowsAdapter extends WindowAdapter {
    
    private SIVOXDevint voix;

    public ScoreWindowsAdapter(SIVOXDevint voix) {
        super();
        this.voix = voix;
    }
    
    @Override
    public void windowClosing(WindowEvent windowEvent)
    {
        this.voix.stop();
        Utils.scoresFrame = null;
    }

}
