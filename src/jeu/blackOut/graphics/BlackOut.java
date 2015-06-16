/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import jeu.base.FenetreSimple;
import jeu.blackOut.graphics.grid.Light;
import jeu.blackOut.graphics.grid.LightGrid;
import jeu.blackOut.utils.Constants;
import jeu.blackOut.utils.Utils;
import jeu.blackOut.utils.helps.Help;
import jeu.blackOut.utils.scores.SavedScore;
import jeu.blackOut.utils.settings.SettingsSave;
import devintAPI.Preferences;

/**
 * The Class BlackOut. This is the frame which contains the game : game
 * informations, grid etc..
 */
public class BlackOut extends FenetreSimple implements ActionListener {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8295763419894931073L;

    /** The panels. */
    private JPanel northPanel, southPanel;

    /** The center panel. */
    private LightGrid centerPanel;

    /** The content pane (frame). */
    private Container contentPane;

    /** The quit button. */
    private JButton returnButton;

    /** The save button. */
    private JButton saveButton;

    /** The retry button. */
    private JButton retryButton;

    /** The current level. */
    private int currentLevel;

    private int currentClickNumber;

    /** The indice (ex: grid 3x3 -> 3 times). */
    private int indice;

    /** The level label. */
    private JLabel level;

    /** The pseudo. */
    private JLabel pseudo;

    /** The grid saved. */
    private ArrayList<Boolean> gridSave;

    /** Le fichier de sauvegarde des scores */
    private SavedScore savedScore;

    private String pseudoPlayer;

    private JLabel clickNumber;

    private int x = 0;

    private int y = 0;

    private LevelChange changeLvl;

    private String aideF1Jeu = "../ressources/sons/F1/aideF1Jeu.wav";

    @Override
    protected String wavRegleJeu() {
        return aideF1Jeu;
    }

    /**
     * Instantiates a new game.
     */
    public BlackOut() {
        super("BlackOut");

        this.contentPane = this.getContentPane();

        this.pseudoPlayer = Utils.pseudo;

        this.currentClickNumber = 0;

        this.savedScore = new SavedScore();

        this.currentLevel = 1;

        // Setting frame
        this.setFrame();

        Utils.gameLaunched = true;

        Thread thread = new Thread(){
            public void run(){
                voix.stop();
                voix.playWav(aideF1Jeu);

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Launching music if enabled
                Utils.launchGameMusic();
         }
            };
        thread.start();
        }

    /**
     * Sets the frame.
     */
    private void setFrame() {
        // Action on close
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                BlackOut.actionOnClose();
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

        // Actualize frame
        Utils.refresh(contentPane);
    }

    /**
     * Sets the north panel.
     */
    private void setNorthPanel() {
        this.northPanel = new JPanel();
        this.northPanel.setLayout(new BorderLayout());
        Utils.applyAppearance(this.northPanel);

        // // Title
        // JLabel title = new JLabel("BLACKOUT");
        // Utils.applyAppearance(title);
        // title.setHorizontalAlignment(SwingConstants.CENTER);
        // this.northPanel.add(title, BorderLayout.NORTH);

        // Pseudo
        pseudo = new JLabel(" [" + this.pseudoPlayer + "]");
        Utils.applyAppearance(pseudo);
        pseudo.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(pseudo, BorderLayout.WEST);

        // Number of click
        clickNumber = new JLabel("Clics : " + this.currentClickNumber + " ");
        Utils.applyAppearance(clickNumber);
        clickNumber.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(clickNumber, BorderLayout.EAST);

        // Current level
        level = new JLabel("Niveau : " + this.currentLevel);
        Utils.applyAppearance(level);
        level.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(level, BorderLayout.CENTER);

        this.contentPane.add(northPanel, BorderLayout.NORTH);
    }

    /**
     * Sets the center panel.
     */
    private void setCenterPanel() {
        // Create the first grid
        this.setNewGrid(Constants.INITIAL_NUMBER_OF_ROW,
                Constants.INITIAL_NUMBER_OF_COLUMN,
                Constants.INITIAL_DIFFICULTY);
    }

    /**
     * Sets the south panel.
     */
    private void setSouthPanel() {
        this.southPanel = new JPanel();
        this.southPanel.setBackground(Preferences.getData()
                .getCurrentBackgroundColor());

        // Retry button
        retryButton = new JButton("Réessayer");
        Utils.applyAppearance(retryButton);
        retryButton.addActionListener(this);
        this.southPanel.add(retryButton);

        // Save button
        saveButton = new JButton("Enregistrer le score");
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

    /*
     * (non-Javadoc)
     * 
     * @see jeu.base.FenetreSimple#init()
     */
    @Override
    public void init() {
    }

    /**
     * Sets a new grid.
     * 
     * @param rows
     *            the rows
     * @param columns
     *            the columns
     * @param difficulty
     *            the difficulty
     */
    public void setNewGrid(int rows, int columns, int difficulty) {
        // Remove the previous grid
        try {
            this.centerPanel.removeAll();
            this.contentPane.remove(centerPanel);
        } catch (NullPointerException e) {
        }

        if (this.currentLevel > 1) {
            String str = "  Niveau " + (this.currentLevel - 1) + " réussi !";
            changeLvl = new LevelChange(str);
            changeLvl.setVisible(true);
        }

        // Generating the new grid
        this.centerPanel = new LightGrid(this, rows, columns, difficulty);
        this.contentPane.add(centerPanel, BorderLayout.CENTER);

        this.indice++;

        // mmh...
        // Utils.gridSave = this.centerPanel;
        this.saveGrid();

        // setting the border
        x = 0;
        y = 0;
        Border border = BorderFactory.createLineBorder(Color.RED, 5);
        this.centerPanel.getLightAt(x, y).setBorder(
                BorderFactory.createCompoundBorder(border,
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Applying panel appearance
        Utils.applyAppearance(this.centerPanel);

        // Actualize the panel
        Utils.refresh(contentPane);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        voix.stop();
        super.keyPressed(e);
        Border defaultBorder = BorderFactory.createLineBorder(Color.CYAN, 1);
        this.centerPanel.getLightAt(x, y).setBorder(
                BorderFactory.createCompoundBorder(defaultBorder,
                        BorderFactory.createEmptyBorder(1, 1, 1, 1)));

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Utils.gameLaunched = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_F2) {
            voix.stop();
            voix.playText(Help.jeu);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (x > 0) {
                x--;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (x < this.centerPanel.getNbRow() - 1) {
                x++;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (y < this.centerPanel.getNbColumn() - 1) {
                y++;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (y > 0) {
                y--;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_F1) {

            Utils.stopGameMusic();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            Utils.launchGameMusic();

        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.centerPanel.getLightAt(x, y).onPlayerClick();
        }
        Border border = BorderFactory.createLineBorder(Color.RED, 5);
        this.centerPanel.getLightAt(x, y).setBorder(
                BorderFactory.createCompoundBorder(border,
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    /**
     * Save the grid.
     */
    private void saveGrid() {
        int nbRows = this.centerPanel.getNbRow();
        int nbColumns = this.centerPanel.getNbColumn();

        this.gridSave = new ArrayList<Boolean>();

        for (int i = 0; i < nbRows; i++) {
            for (int j = 0; j < nbColumns; j++) {
                Light light = this.centerPanel.getLightAt(i, j);
                this.gridSave.add(light.isLightOn());
            }
        }
    }

    /**
     * Retry.
     */
    public void retry() {
        int nbRows = this.centerPanel.getNbRow();
        int nbColumns = this.centerPanel.getNbColumn();

        // Remove the previous grid
        try {
            this.centerPanel.removeAll();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < nbRows; i++) {
            for (int j = 0; j < nbColumns; j++) {
                boolean lightOn = this.gridSave.get(nbRows * i + j);
                Light light = new Light(i, j, centerPanel, this, lightOn);
                this.centerPanel.add(light);
            }
        }

        Utils.refresh(centerPanel);
    }

    @Override
    public void changeColor() {
        this.changeColorPanel(this.northPanel);
        Utils.applyAppearance(this.centerPanel);
        this.changeColorPanel(this.southPanel);
    }

    /*
     * (non-Javadoc)
     * 
     * @see jeu.base.FenetreSimple#changeSize()
     */
    @Override
    public void changeSize() {
        this.changeSizePanel(northPanel);
        this.changeSizePanel(southPanel);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        // Toujours stopper la voix avant de parler
        voix.stop();
        // on récupère la source de l'évènement
        Object source = ae.getSource();
        // si c'est le bouton "ecrire"

        if (source.equals(returnButton)) {
            String text = "Retour";
            voix.playText(text);

            BlackOut.actionOnClose();

            this.dispose();
        }

        else if (source.equals(saveButton)) {
            String text = "Sauvegarder";
            voix.playText(text);
            new LevelChange(" Partie sauvegardée");
            savedScore.writeScore(Utils.pseudo, this.currentLevel);
        }

        else if (source.equals(retryButton)) {
            String text = "Réessayer";
            voix.playText(text);

            this.retry();
        }
    }

    /**
     * Increases the level.
     */
    public void increaseLevel() {
        // Modifying level
        this.level.setText("Niveau : " + ++this.currentLevel);

        // Actualize the level displayed
        Utils.refresh(northPanel);
    }

    public void increaseClickNumber() {
        // Modifying number of click
        this.clickNumber.setText("Clics : " + ++this.currentClickNumber + " ");

        // Actualize the click number displayed
        Utils.refresh(northPanel);
    }

    public void resetClickNumber() {
        this.currentClickNumber = 0;
        this.clickNumber.setText("Clics : " + this.currentClickNumber + " ");
    }

    /**
     * Action on close.
     */
    public static void actionOnClose() {
        // Stop the game music
        Utils.stopGameMusic();

        Utils.gameLaunched = false;
    }

    /**
     * Play victory sound.
     */
    public void playVictorySound() {
        voix.stop();
        if (SettingsSave.epilepticEnabled) {
            voix.playWav(Constants.VICTORY_SOUND_PATH_ONE);
            // try
            // {
            // Thread.sleep(1500);
            // }
            // catch (InterruptedException e)
            // {
            // e.printStackTrace();
            // }
        } else {
            voix.playWav(Constants.VICTORY_SOUND_PATH_TWO);

            // try
            // {
            // Thread.sleep(1500);
            // }
            // catch (InterruptedException e)
            // {
            // e.printStackTrace();
            // }

        }

        voix.playText("Bravo. Vous êtes maintenant au niveau "
                + Integer.toString(this.currentLevel));
    }

    /**
     * Gets the indice.
     * 
     * @return the indice
     */
    public int getIndice() {
        return indice;
    }

    /**
     * Sets the indice.
     * 
     * @param indice
     *            the new indice
     */
    public void setIndice(int indice) {
        this.indice = indice;
    }
}
