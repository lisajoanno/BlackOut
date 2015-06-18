/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.utils.settings;

import jeu.blackOut.utils.Constants;


/**
 * The Class CurrentSettings. These are the current settings.
 */
public abstract class CurrentSettings {
	
	/** The color chosen. */
	public static int currentColor;
	
	/** The size chosen. */
	public static int currentSize;
	
	/** The voice chosen. */
	public static int currentVoice;
	
	/** The epileptic mode statement. */
	public static boolean epilepticMode;
	
	/**
	 * Increase current color.
	 */
	public static void increaseCurrentColor() {
		currentColor++;
		if (currentColor > Constants.NUMBER_OF_COLOR)
			currentColor = 1;
	}
	
	/**
	 * Increase current size.
	 */
	public static void increaseCurrentSize() {
		currentSize++;
		if (currentSize > Constants.NUMBER_OF_SIZE)
			currentSize = 1;
	}
	
	/**
	 * Increase current voice.
	 */
	public static void increaseCurrentVoice() {
		currentVoice++;
		if (currentVoice > Constants.NUMBER_OF_VOICE)
			currentVoice = 1;
	}
	
	/**
	 * Inits the current settings.
	 */
	public static void init() {
	    
	    SettingsSave.readSettings();

		currentColor = SettingsSave.colorSaved;
		currentSize = SettingsSave.sizeSaved;
		currentVoice = SettingsSave.voiceSaved;
		epilepticMode = SettingsSave.epilepticEnabled;
	}

}
