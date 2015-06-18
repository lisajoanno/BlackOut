/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.utils;

import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import jeu.blackOut.graphics.BlackOut;
import jeu.blackOut.graphics.Instructions;
import jeu.blackOut.graphics.InstructionsImage;
import jeu.blackOut.graphics.PlayerInfo;
import jeu.blackOut.graphics.Scores;
import jeu.blackOut.graphics.Settings;
import jeu.blackOut.utils.settings.SettingsSave;
import devintAPI.Preferences;

/**
 * The Class Utils which contains useful things.
 */
public class Utils {

    /** The current music which is played by the BlackOut game. */
    public static Audio music;

    /** The epileptic mode. */
    public static HardcoreMode hardcoreMode;

    /** The game frame. */
    public static BlackOut blackOutFrame;

    /** The exemple frame. */
    public static InstructionsImage imgFrame;

    /** The settings frame. */
    public static Settings settingsFrame;

    /** The scores frame. */
    public static Scores scoresFrame;

    /** The instructions frame. */
    public static Instructions instructionsFrame;

    /** The player info frame. */
    public static PlayerInfo playerInfoFrame;

    /** The player's pseudo */
    public static String pseudo;

    /** The game launched statement. */
    public static boolean gameLaunched = false;

    public static String instructions = "\n\nBlack Out est un jeu de type puzzle. "
            + "Le jeu vous propose une grille contenant un certain nombre de cases. Chaque case représente une lampe pouvant avoir deux états : allumée ou éteinte. Le but du jeu est simple : éteindre toutes les lumières. La difficulté réside dans le fait que l’allumage ou l’extinction d’une lampe provoque le changement d’état des lampes adjacentes. "
            + "Le jeu est de type « Survival » (survie) : lorsque toutes les lumières sont éteintes, une nouvelle grille plus complexe apparaît.";

    /**
     * Refresh a GUI component.
     * 
     * @param c
     *            the c
     */
    public static void refresh(Component c) {
        c.validate();
        c.revalidate();
        c.repaint();
    }

    /**
     * Apply appearance to a component.
     * 
     * @param comp
     *            the component
     */
    public static void applyAppearance(Component comp) {
        // Setting font
        comp.setFont(Preferences.getData().getCurrentFont());

        // Setting background
        comp.setBackground(Preferences.getData().getCurrentBackgroundColor());

        // Setting foreground
        comp.setForeground(Preferences.getData().getCurrentForegroundColor());
    }

    /**
     * Stop game music.
     */
    @SuppressWarnings("deprecation")
    public static void stopGameMusic() {
        if (Utils.music != null)
            Utils.music.stop();

    }

    /**
     * Launch game music.
     */
    public static void launchGameMusic() {
        if (!SettingsSave.musicDisabled && Utils.gameLaunched
                && (Utils.settingsFrame == null)) {
            Utils.music = new Audio(Constants.MUSIC_PATH
                    + SettingsSave.musicChosen);
            Utils.music.start();
        }
    }

    public static void copyFile(String sourcePath, String destPath) {
        FileChannel in = null; // canal d'entrée
        FileChannel out = null; // canal de sortie

        try {
            // Init
            in = new FileInputStream(sourcePath).getChannel();
            out = new FileOutputStream(destPath).getChannel();

            // Copie depuis le in vers le out
            in.transferTo(0, in.size(), out);
        } catch (Exception e) {
            e.printStackTrace(); // n'importe quelle exception
        } finally { // finalement on ferme
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
