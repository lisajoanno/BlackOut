package jeu.blackOut.utils.instructions;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jeu.blackOut.utils.Utils;
import t2s.SIVOXDevint;


public class InstructionImageWindowsAdapter extends WindowAdapter {
    
    private SIVOXDevint voix;

    public InstructionImageWindowsAdapter(SIVOXDevint voix) {
        super();
        this.voix = voix;
    }
    
    @Override
    public void windowClosing(WindowEvent windowEvent)
    {
        this.voix.stop();
        Utils.imgFrame = null;
    }

}
