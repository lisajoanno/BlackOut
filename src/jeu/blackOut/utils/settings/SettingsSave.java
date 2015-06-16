/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.utils.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import jeu.blackOut.utils.Constants;
import jeu.blackOut.utils.HardcoreMode;
import jeu.blackOut.utils.Utils;
import devintAPI.Preferences;

/**
 * The Class SettingsSave contains all game settings (colors, size, music
 * etc..).
 */
public abstract class SettingsSave {

    /** The music chosen. */
    public static String musicChosen = "music.wav";
    
    /** The param music chosen. */
    public static String paramMusicChosen = "musicChosen:" + musicChosen + "\n";

    /** The color chosen. */
    public static int colorSaved;
    
    /** The param color saved. */
    public static String paramColorSaved = "colorSaved:" + colorSaved + "\n";

    /** The size chosen. */
    public static int sizeSaved;
    
    /** The param size saved. */
    public static String paramSizeSaved = "sizeSaved:" + sizeSaved + "\n";

    /** The voice chosen. */
    public static int voiceSaved;
    
    /** The param voice saved. */
    public static String paramVoiceSaved = "voiceSaved:" + voiceSaved + "\n";

    /** The music statement. */
    public static boolean musicDisabled = false;
    
    /** The param music disabled. */
    public static String paramMusicDisabled = "musicDisabled:" + musicDisabled
            + "\n";

    /** The epileptic enabled. */
    public static boolean epilepticEnabled = false;
    
    /** The param epileptic enabled. */
    public static String paramEpilepticEnabled = "epilepticEnabled:"
            + epilepticEnabled + "\n";

    /**
     * Apply settings.
     */
    public static void applySettings() {
        //CurrentSettings.init();
        readSettings();
        Preferences pref = Preferences.getData();
        for (int i = 1; i <= SettingsSave.colorSaved; i++)
            pref.changeColor();
        for (int i = 1; i <= SettingsSave.sizeSaved; i++)
            pref.changeSize();
        for (int i = 1; i <= SettingsSave.voiceSaved; i++)
            pref.changeVoice();
        if (SettingsSave.epilepticEnabled) {
            Utils.hardcoreMode = new HardcoreMode();
            Utils.hardcoreMode.start();
        }
    }

    /**
     * This methods refreshes the settings.
     */
    private static void refreshSettings() {
        paramMusicChosen = "musicChosen:" + musicChosen + "\n";
        paramColorSaved = "colorSaved:" + colorSaved + "\n";
        paramSizeSaved = "sizeSaved:" + sizeSaved + "\n";
        paramVoiceSaved = "voiceSaved:" + voiceSaved + "\n";
        paramMusicDisabled = "musicDisabled:" + musicDisabled + "\n";
        paramEpilepticEnabled = "epilepticEnabled:" + epilepticEnabled + "\n";
    }

    /**
     * A method writing in a new file (.txt) the current settings (when the
     * player changes the settings and hits the "save" button). The method
     * gathers the settings the player just saved and writes them in a new file
     * settings.txt. If the file already exists, the method creates it.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeNewSettings() throws IOException {
        refreshSettings();
        String fileName = Constants.RESOURCES_PATH + "settings.txt";
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(paramMusicChosen);
            bw.write(paramColorSaved);
            bw.write(paramSizeSaved);
            bw.write(paramVoiceSaved);
            bw.write(paramMusicDisabled);
            bw.write(paramEpilepticEnabled);
            
            bw.close();
        } catch (IOException e) {
        } catch (SecurityException e) {
        }
        ;

    }

    /**
     * A method reading the settings in settings.txt, and implementing them to
     * the current game.
     */
    public static void readSettings() {
        File file = new File(Constants.RESOURCES_PATH + "settings.txt");
        BufferedReader reader;
        String lign;
        try {
            reader = new BufferedReader(new FileReader(file));
            do {
                lign = reader.readLine();
                if (lign != null) {
                    detectSettingsInLignFile(lign);
                }
            } while (lign != null);
            reader.close();
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
        ;
    }


    /**
     * Detect settings in lign file.
     *
     * @param lign the lign
     * @return the string
     */
    public static String detectSettingsInLignFile(String lign) {
        int index = lign.indexOf(':');
        String parameterName = lign.substring(0, index);
        String parameterValue = lign.substring(index + 1);
        applyParameter(parameterName, parameterValue);
        return null;
    }


    /**
     * Apply parameter.
     *
     * @param name the name
     * @param value the value
     */
    public static void applyParameter(String name, String value) {
        if (name.equals("musicChosen")) {
            SettingsSave.musicChosen = value;
        } else if (name.equals("colorSaved")) {
            SettingsSave.colorSaved = Integer.parseInt(value);
        } else if (name.equals("sizeSaved")) {
            SettingsSave.sizeSaved = Integer.parseInt(value);
        } else if (name.equals("voiceSaved")) {
            SettingsSave.voiceSaved = Integer.parseInt(value);
        } else if (name.equals("musicDisabled")) {
            SettingsSave.musicDisabled = Boolean.parseBoolean(value);
        } else if (name.equals("epilepticEnabled")) {
            SettingsSave.epilepticEnabled = Boolean.parseBoolean(value);
        } else {
        }
    }

}
