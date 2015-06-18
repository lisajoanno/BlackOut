/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.graphics;

import java.awt.event.KeyEvent;

import jeu.base.MenuJeu;
import jeu.blackOut.utils.Utils;
import jeu.blackOut.utils.helps.Help;

/**
 * The Class Menu which displays options.
 */
public class Menu extends MenuJeu {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new menu frame.
     */
    public Menu() {
        super("BlackOut");
        voix.stop();
        voix.playWav(aideF1Menu);
    }

    private String aideF1Menu = "../ressources/sons/F1/aideF1Menu.wav";
    
    @Override
    protected String wavRegleJeu() {
        return aideF1Menu;
    }
    
    @Override
    protected String[] nomOptions() {
        // Options
        String[] noms = { "Jeu", "Scores", "Paramètres", "Instructions",
                "Exemple", "Quitter" };
        return noms;
    }

    @Override
    protected void lancerOption(int i) {
        // Actions
        switch (i) {
        case 0:
            if (!Utils.gameLaunched) {
                new PlayerInfo();
            } else
                Utils.blackOutFrame.requestFocus();
            break;
        case 2:
            if (Utils.settingsFrame == null) {
                Utils.settingsFrame = new Settings();
            } else
                Utils.settingsFrame.requestFocus();
            break;
        case 3:
            if (Utils.instructionsFrame == null) {
                Utils.instructionsFrame = new Instructions();
            } else
                Utils.instructionsFrame.requestFocus();
            break;

        case 1:
            if (Utils.scoresFrame == null) {
                Utils.scoresFrame = new Scores();
            } else {
                Utils.scoresFrame.requestFocus();
            }

            break;

        case 5:
            System.exit(0);
        case 4:
            if (Utils.imgFrame == null) {
                Utils.imgFrame = new InstructionsImage();
            } else {
                Utils.imgFrame.requestFocus();
            }
                       
            break;
        default:
            System.err.println("action non définie");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        voix.stop();
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_F2) {
            voix.stop();
            voix.playText(Help.menu);
        }
    }

}
