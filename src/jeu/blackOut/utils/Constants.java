/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.utils;

import java.awt.Color;


public abstract class Constants {

	/** The INITIAL_NUMBER_OF_ROW of the light grid. */
	public final static int INITIAL_NUMBER_OF_ROW = 2;

	/** The INITIAL_NUMBER_OF_COLUMN of the light grid. */
	public final static int INITIAL_NUMBER_OF_COLUMN = 2;

	/** The INITIAL_DIFFICULTY of the light grid. */
	public final static int INITIAL_DIFFICULTY = 1;

    public static final String RESOURCES_PATH = "../ressources/";

	public final static String MUSIC_PATH = "../ressources/sons/";
	
	public final static String ICON_PATH = "../ressources/images/icon.png";
	
	public final static String VICTORY_SOUND_PATH_ONE = "../ressources/sons/victory.wav";
	
	public final static String VICTORY_SOUND_PATH_TWO = "../ressources/sons/victory2.wav";
	
	public final static String DESKTOP_PATH = "C:/Users/" + System.getProperty("user.name") + "/Desktop/";

	/** The NUMBER_OF_VOICE. */
	public final static int NUMBER_OF_VOICE = 8;

	/** The NUMBER_OF_COLOR. */
	public final static int NUMBER_OF_COLOR = 3;

	/** The NUMBER_OF_SIZE. */
	public final static int NUMBER_OF_SIZE = 3;

	/** The light color. */
	public final static Color LIGHT_COLOR = Color.yellow;

	/** The unlight color. */
	public  final static Color UNLIGHT_COLOR = Color.black;


}
