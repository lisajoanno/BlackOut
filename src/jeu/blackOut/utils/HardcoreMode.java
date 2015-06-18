/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.utils;

import jeu.blackOut.utils.settings.CurrentSettings;
import devintAPI.Preferences;

/**
 * The Class Audio. Used to launch a music.
 */
public class HardcoreMode extends Thread {

	/**
	 * Instantiates a new audio.
	 * 
	 * 
	 */
	public HardcoreMode()
	{
		super();
	}

	public void run()
	{
		while (true)
		{
			Preferences.getData().changeColor();
			CurrentSettings.increaseCurrentColor();
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{}
		}
	}
}
