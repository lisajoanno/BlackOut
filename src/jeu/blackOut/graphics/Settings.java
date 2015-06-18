/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jeu.base.FenetreSimple;
import jeu.blackOut.utils.Audio;
import jeu.blackOut.utils.Constants;
import jeu.blackOut.utils.HardcoreMode;
import jeu.blackOut.utils.Utils;
import jeu.blackOut.utils.helps.Help;
import jeu.blackOut.utils.settings.CurrentSettings;
import jeu.blackOut.utils.settings.SettingsSave;
import devintAPI.Preferences;

/**
 * The Class Settings. This is the frame which allows to modify game settings.
 */

public class Settings extends FenetreSimple implements ActionListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4332569832942270163L;

	/** The panels. */
	JPanel northPanel, centerPanel, southPanel;

	/** The content pane. */
	private Container contentPane;

	// - NORTH
	/** The title. */
	private JLabel title;

	// - CENTER
	// Music
	/** The music label. */
	private JLabel musicLbl;

	/** The music statement. */
	private JCheckBox disableMusic;

	/** The music list. */
	private JComboBox<String> comboMusic;

	/** The import music. */
	private JButton importMusic;

	/** The listen music. */
	private JButton listenMusic;

	/** The stop music. */
	private JButton stopMusic;

	/** The temporary music. */
	private static Audio musicTemp;

	// Color
	/** The color label. */
	private JLabel colorLbl;

	/** The epileptic mode statement. */
	private JCheckBox enableHardcoreMode;

	/** The color selected. */
	private JLabel colorSelected;

	/** The next color. */
	private JButton nextColor;

	// Size
	/** The size selected. */
	private JLabel sizeSelected;

	/** The next size. */
	private JButton nextSize;

	// Voice
	/** The voice selected. */
	private JLabel voiceSelected;

	/** The next voice. */
	private JButton nextVoice;

	// - SOUTH
	/** The quit button. */
	private JButton returnButton;

	/** The save button. */
	private JButton saveButton;

	private JPanel musicPanel;

	private JPanel musicPanel2;

	private JPanel twoMusicButtonPane;

	private JPanel colorPanel;

	/**
	 * Instantiates a new setting frame.
	 */
	public Settings()
	{
		super("Paramètres");
		voix.stop();
		voix.playWav(aideF1Settings);

		this.contentPane = this.getContentPane();

		// Pause the music if launched
		Utils.stopGameMusic();

		// Setting the frame
		this.setFrame();

		// Loading settings saved
		this.loadSettings();

	}
	
	private String aideF1Settings = "../ressources/sons/F1/aideF1Paramètres.wav";
	
    @Override
    protected String wavRegleJeu() {
        return aideF1Settings;
    }

	/**
	 * Useless function.
	 */
	@Override
	public void init()
	{}

	/**
	 * Sets the frame.
	 */
	private void setFrame()
	{
		// Action on close
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent windowEvent)
			{
				Settings.actionOnClose();
			}

		});

		// Others
		this.setResizable(false);
		this.setVisible(true);

		// Frame panels
		this.contentPane.setLayout(new BorderLayout());
		Utils.applyAppearance(this.contentPane);

		this.setNorthPanel();
		this.setCenterPanel();
		this.setSouthPanel();

		// Actualize the frame
		Utils.refresh(contentPane);
	}

	/**
	 * Sets the south panel.
	 */
	private void setSouthPanel()
	{
		this.southPanel = new JPanel();
		Utils.applyAppearance(this.southPanel);

		// Save button
		saveButton = new JButton("Sauvegarder");
		Utils.applyAppearance(saveButton);
		saveButton.addActionListener(this);
		this.southPanel.add(saveButton);

		// Return button
		returnButton = new JButton("Retour");
		Utils.applyAppearance(returnButton);
		returnButton.addActionListener(this);
		this.southPanel.add(returnButton);

		this.contentPane.add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * Sets the center panel.
	 */
	private void setCenterPanel()
	{
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new GridLayout(0, 3));
		Utils.applyAppearance(this.centerPanel);

		// Music
		musicPanel = new JPanel();
		musicPanel.setLayout(new BorderLayout());
		Utils.applyAppearance(musicPanel);

		musicLbl = new JLabel("[Musique]");
		Utils.applyAppearance(musicLbl);
		musicLbl.setHorizontalAlignment(SwingConstants.CENTER);
		musicPanel.add(musicLbl, BorderLayout.CENTER);

		disableMusic = new JCheckBox("Désactiver la musique");
		Utils.applyAppearance(disableMusic);
		disableMusic.addActionListener(this);
		disableMusic.setHorizontalAlignment(SwingConstants.CENTER);
		musicPanel.add(disableMusic, BorderLayout.SOUTH);

		this.centerPanel.add(musicPanel);

		musicPanel2 = new JPanel();
		Utils.applyAppearance(musicPanel2);
		musicPanel2.setLayout(new BorderLayout());

		comboMusic = new JComboBox<String>();
		comboMusic.addActionListener(this);
		ArrayList<String> musics = this.getMusics();
		for (String str : musics)
		{
			comboMusic.addItem(str);
		}
		Utils.applyAppearance(comboMusic);
		musicPanel2.add(comboMusic, BorderLayout.CENTER);

		importMusic = new JButton("Importer");
		Utils.applyAppearance(importMusic);
		importMusic.addActionListener(this);
		musicPanel2.add(importMusic, BorderLayout.SOUTH);

		this.centerPanel.add(musicPanel2);

		twoMusicButtonPane = new JPanel();
		twoMusicButtonPane.setLayout(new BorderLayout());

		listenMusic = new JButton("Ecouter");
		listenMusic.addActionListener(this);
		Utils.applyAppearance(listenMusic);
		twoMusicButtonPane.add(listenMusic, BorderLayout.CENTER);

		stopMusic = new JButton("Stop");
		this.stopMusic.setEnabled(false);
		stopMusic.addActionListener(this);
		Utils.applyAppearance(stopMusic);
		twoMusicButtonPane.add(stopMusic, BorderLayout.EAST);

		this.centerPanel.add(twoMusicButtonPane);

		// Color
		colorPanel = new JPanel();
		Utils.applyAppearance(colorPanel);
		colorPanel.setLayout(new BorderLayout());

		colorLbl = new JLabel("[Couleur]");
		Utils.applyAppearance(colorLbl);
		colorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		colorPanel.add(colorLbl, BorderLayout.CENTER);

		enableHardcoreMode = new JCheckBox("Mode hardcore");
		enableHardcoreMode.setHorizontalAlignment(SwingConstants.CENTER);
		Utils.applyAppearance(enableHardcoreMode);
		enableHardcoreMode.addActionListener(this);
		colorPanel.add(enableHardcoreMode, BorderLayout.SOUTH);

		centerPanel.add(colorPanel);

		colorSelected = new JLabel("Couleur n°1");
		Utils.applyAppearance(colorSelected);
		colorSelected.setHorizontalAlignment(SwingConstants.CENTER);
		this.centerPanel.add(colorSelected);

		nextColor = new JButton("Suivante");
		nextColor.addActionListener(this);
		Utils.applyAppearance(nextColor);
		this.centerPanel.add(nextColor);

		// Size
		JLabel sizeLbl = new JLabel("[Taille]");
		Utils.applyAppearance(sizeLbl);
		sizeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		this.centerPanel.add(sizeLbl);

		sizeSelected = new JLabel("Taille n°1");
		Utils.applyAppearance(sizeSelected);
		sizeSelected.setHorizontalAlignment(SwingConstants.CENTER);
		this.centerPanel.add(sizeSelected);

		nextSize = new JButton("Suivante");
		nextSize.addActionListener(this);
		Utils.applyAppearance(nextSize);
		this.centerPanel.add(nextSize);

		// Voice
		JLabel voiceLbl = new JLabel("[Voix]");
		Utils.applyAppearance(voiceLbl);
		voiceLbl.setHorizontalAlignment(SwingConstants.CENTER);
		this.centerPanel.add(voiceLbl);

		voiceSelected = new JLabel("Voix n°1");
		Utils.applyAppearance(voiceSelected);
		voiceSelected.setHorizontalAlignment(SwingConstants.CENTER);
		this.centerPanel.add(voiceSelected);

		nextVoice = new JButton("Suivante");
		nextVoice.addActionListener(this);
		Utils.applyAppearance(nextVoice);
		this.centerPanel.add(nextVoice);

		this.contentPane.add(centerPanel, BorderLayout.CENTER);
	}

	/**
	 * Sets the north panel.
	 */
	private void setNorthPanel()
	{
		this.northPanel = new JPanel();
		this.northPanel.setLayout(new BorderLayout());
		Utils.applyAppearance(this.northPanel);

		// Title
		title = new JLabel("PARAMETRES");
		Utils.applyAppearance(title);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		this.northPanel.add(title, BorderLayout.NORTH);

		this.contentPane.add(northPanel, BorderLayout.NORTH);

	}

	/**
	 * Gets the musics for resources directory.
	 * 
	 * @return the musics
	 */
	private ArrayList<String> getMusics()
	{
		File musicDirectory;
		String[] fileList;
		ArrayList<String> musicList;

		// Getting resources directory
		musicDirectory = new File(Constants.MUSIC_PATH);

		// Getting all files of it
		fileList = musicDirectory.list();
		musicList = new ArrayList<String>();

		// Sorting music files
		for (int i = 0; i < fileList.length; i++)
		{
			if (fileList[i].startsWith("music"))
			{
				musicList.add(fileList[i]);
			}
		}

		return musicList;

	}

	/**
	 * Action performed.
	 * 
	 * @param ae
	 *            the event
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		// Toujours stopper la voix avant de parler
		voix.stop();

		// On récupère la source de l'évènement
		Object source = ae.getSource();
		// Si c'est le bouton "ecrire"

		// Return button
		if (source.equals(returnButton))
		{
			Settings.actionOnClose();

			String text = "Retour";
			voix.playText(text);

			Utils.settingsFrame = null;
			this.dispose();
		}
		// Save button
		else if (source.equals(saveButton))
		{
			String text = "Sauvegarder";
			voix.playText(text);

			this.saveSettings();

			try
			{
				SettingsSave.writeNewSettings();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			Settings.actionOnClose();
			this.dispose();

		}
		// Listen music button
		else if (source.equals(this.listenMusic))
		{
			// Stop the music currently launched
			if (Settings.musicTemp != null)
				Settings.musicTemp.stop();

			// Getting music selected
			String musicToListen = (String) this.comboMusic.getSelectedItem();

			// Launching music
			Settings.musicTemp = new Audio(Constants.MUSIC_PATH + musicToListen);
			Settings.musicTemp.start();

			// Enable stop button
			this.stopMusic.setEnabled(true);

		}
		// Stop button
		else if (source.equals(this.stopMusic))
		{
			// Stop the music currently launched
			if (Settings.musicTemp != null)
				Settings.musicTemp.stop();

			// Disable the button
			this.stopMusic.setEnabled(false);
		}
		// Import music
		else if (source.equals(this.importMusic))
		{

			String text = "Importer";
			voix.playText(text);
			this.importMusic();

		}
		// Next color button
		else if (source.equals(this.nextColor))
		{
			// Change the frame color
			Preferences.getData().changeColor();

			// Actualize color number displayed
			this.colorSelected.setText("Couleur n°" + CurrentSettings.currentColor);

			String text = "Couleur numéro " + CurrentSettings.currentColor;
			voix.playText(text);
		}
		// Next size button
		else if (source.equals(this.nextSize))
		{
			// Change the font size
			Preferences.getData().changeSize();

			// Actualize size number displayed
			this.sizeSelected.setText("Taille n°" + CurrentSettings.currentSize);

			String text = "Taille numéro " + CurrentSettings.currentSize;
			voix.playText(text);

		}
		// Next voice button
		else if (source.equals(this.nextVoice))
		{
			// Change the voice
			Preferences.getData().changeVoice();

			// Actualize voice number displayed
			this.voiceSelected.setText("Voix n°" + CurrentSettings.currentVoice);

			String text = "Voix numéro " + CurrentSettings.currentVoice;
			voix.playText(text);

		}
		// Epileptic mode
		else if (source.equals(this.enableHardcoreMode))
		{
			if (this.enableHardcoreMode.isSelected())
			{
				// Epileptic mode activated
				CurrentSettings.epilepticMode = true;

				// Disable the personal color modifying
				this.nextColor.setEnabled(false);

				// Launch the mode
				Utils.hardcoreMode = new HardcoreMode();
				Utils.hardcoreMode.start();
			}
			else
			{
				// Epileptic mode deactivated
				CurrentSettings.epilepticMode = false;

				// Enable the personal color modifying
				this.nextColor.setEnabled(true);

				// Stop the mode
				Utils.hardcoreMode.stop();
			}

		}

		// Frame is focused for F1 F2 F3 F4 F5 actions.
		this.requestFocus();

	}

	public void keyPressed(KeyEvent e)
	{
		voix.stop();
		super.keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			Utils.settingsFrame = null;
		}
		if (e.getKeyCode() == KeyEvent.VK_F2)
		{
			voix.stop();
			voix.playText(Help.param);
		}
	}

	/**
	 * Import music (wav file only).
	 */
	private void importMusic()
	{
		// Launching a browser
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Importer une musique");
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.setCurrentDirectory(new File(Constants.DESKTOP_PATH));

		// Filtering wav files
		FileFilter fileFilter = new FileNameExtensionFilter("Musiques '.wav'", "wav");
		jfc.setFileFilter(fileFilter);
		jfc.addChoosableFileFilter(fileFilter);
		jfc.setAcceptAllFileFilterUsed(false);

		int returnVal = jfc.showOpenDialog(this);

		// If the file is chosen
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File file = jfc.getSelectedFile();
			String pathFile = file.getAbsolutePath();

			// System.out.println("Source: " + file.getName());

			// Do not accept directory files
			if (file.isDirectory())
				JOptionPane.showMessageDialog(this, "Le fichier sélectionné n'est pas valide.");
			else
			{
				// Copying file in the music directory
				String newFileName = "music-" + file.getName();
				Utils.copyFile(pathFile, Constants.MUSIC_PATH + newFileName);

				// Setting the frame music chosen
				this.comboMusic.addItem(newFileName);
				this.comboMusic.setSelectedItem(newFileName);
			}
		}
	}

	/**
	 * Save settings.
	 */
	@SuppressWarnings("deprecation")
	private void saveSettings()
	{
		// Music
		SettingsSave.musicChosen = (String) this.comboMusic.getSelectedItem();
		SettingsSave.musicDisabled = this.disableMusic.isSelected();

		if (SettingsSave.musicDisabled)
			if (Utils.music != null)
				Utils.music.stop();

		// Color
		SettingsSave.colorSaved = this.getLastNumberFromJLabel(this.colorSelected);
		SettingsSave.epilepticEnabled = this.enableHardcoreMode.isSelected();

		// Size
		SettingsSave.sizeSaved = this.getLastNumberFromJLabel(this.sizeSelected);

		// Voice
		SettingsSave.voiceSaved = this.getLastNumberFromJLabel(this.voiceSelected);
	}

	/**
	 * Load settings.
	 */
	private void loadSettings()
	{
		// Music
		this.comboMusic.setSelectedItem(SettingsSave.musicChosen);
		this.disableMusic.setSelected(SettingsSave.musicDisabled);

		// Color
		this.colorSelected.setText("Couleur n°" + CurrentSettings.currentColor);
		this.enableHardcoreMode.setSelected(SettingsSave.epilepticEnabled);
		// Disable the personal color modifying
		if (SettingsSave.epilepticEnabled)
			this.nextColor.setEnabled(false);

		// Size
		this.sizeSelected.setText("Taille n°" + CurrentSettings.currentSize);

		// Voice
		this.voiceSelected.setText("Voix n°" + CurrentSettings.currentVoice);
	}

	/**
	 * Change color.
	 */
	@Override
	public void changeColor()
	{
		this.changeColorPanel(this.northPanel);
		this.changeColorPanel(this.centerPanel);
		this.changeColorPanel(this.southPanel);

		Utils.applyAppearance(this.listenMusic);
		Utils.applyAppearance(this.stopMusic);

		Utils.applyAppearance(this.comboMusic);
		Utils.applyAppearance(this.importMusic);

		Utils.applyAppearance(this.musicLbl);
		Utils.applyAppearance(this.disableMusic);

		Utils.applyAppearance(this.colorLbl);
		Utils.applyAppearance(this.enableHardcoreMode);
	}

	/**
	 * Change size.
	 */
	@Override
	public void changeSize()
	{
		this.changeSizePanel(this.northPanel);
		this.changeSizePanel(this.centerPanel);
		this.changeSizePanel(this.southPanel);

		Utils.applyAppearance(this.listenMusic);
		Utils.applyAppearance(this.stopMusic);

		Utils.applyAppearance(this.comboMusic);
		Utils.applyAppearance(this.importMusic);

		Utils.applyAppearance(this.musicLbl);
		Utils.applyAppearance(this.disableMusic);

		Utils.applyAppearance(this.colorLbl);
		Utils.applyAppearance(this.enableHardcoreMode);
	}

	/**
	 * Gets the last number from a JLabel text.
	 * 
	 * @param lbl
	 *            the label
	 * @return the last number from JLabel
	 */
	private int getLastNumberFromJLabel(JLabel lbl)
	{
		// Example : "Couleur n°2"
		String tempStr = lbl.getText();
		int sizeChosenInt = Integer.parseInt(tempStr.substring(tempStr.length() - 1,
				tempStr.length()));
		return sizeChosenInt;
	}

	/**
	 * Refresh color info.
	 */
	public void refreshColorInfo()
	{
		// Actualize voice number displayed
		this.colorSelected.setText("Couleur n°" + CurrentSettings.currentColor);
	}

	/**
	 * Refresh size info.
	 */
	public void refreshSizeInfo()
	{
		// Actualize voice number displayed
		this.sizeSelected.setText("Taille n°" + CurrentSettings.currentSize);
	}

	/**
	 * Refresh voice info.
	 */
	public void refreshVoiceInfo()
	{
		// Actualize voice number displayed
		this.voiceSelected.setText("Voix n°" + CurrentSettings.currentVoice);
	}

	/**
	 * Action on close.
	 */
	@SuppressWarnings("deprecation")
	public static void actionOnClose()
	{	    
		// Stop the settings music
		if (Settings.musicTemp != null)
			Settings.musicTemp.stop();

		Utils.settingsFrame = null;

		// Resume the game music
		Utils.launchGameMusic();
	}
}
