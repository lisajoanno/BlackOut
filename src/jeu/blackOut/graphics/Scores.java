/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import jeu.base.FenetreSimple;
import jeu.blackOut.utils.Constants;
import jeu.blackOut.utils.Utils;
import jeu.blackOut.utils.scores.SavedScore;
import jeu.blackOut.utils.scores.ScoreWindowsAdapter;

/**
 * The Class Scores.
 */
public class Scores extends FenetreSimple implements ActionListener {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The quit. */
    private JButton quit;

    /** The title. */
    private JLabel title;

    /** The sp. */
    private JScrollPane sp;

    /** The zt. */
    private JTextArea zt;

    /** The content pane. */
    private Container contentPane;

    
    private String aideF1Score = "../ressources/sons/F1/aideF1Scores.wav";
    
    @Override
    protected String wavRegleJeu() {
        return aideF1Score;
    }
    
    /**
     * Instantiates a new scores.
     */
    public Scores()
    {
        super("Scores");
        voix.stop();
        voix.playWav(aideF1Score);

        this.contentPane = this.getContentPane();
        this.contentPane.setLayout(new BorderLayout());

        this.addWindowListener(new ScoreWindowsAdapter(voix));

        title = new JLabel("Scores");
        zt = new JTextArea();
        zt.setEditable(false);
        sp = new JScrollPane(zt);
        quit = new JButton("Retour");

        // title
        this.contentPane.add(title, BorderLayout.NORTH);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        Utils.applyAppearance(title);

        // text area
        this.contentPane.add(sp, BorderLayout.CENTER);
        zt.setWrapStyleWord(true);
        Utils.applyAppearance(zt);
        zt.setLineWrap(true);
        fillTextArea();

        // quit button
        Utils.applyAppearance(quit);
        quit.addActionListener(this);
        this.contentPane.add(quit, BorderLayout.SOUTH);

        Utils.applyAppearance(contentPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Fill text area.
     */
    public void fillTextArea()
    {
        
        SavedScore ss = new SavedScore();
        ss.sortScore();
        File file = new File(Constants.RESOURCES_PATH + "score.txt");
        BufferedReader reader;
        String line;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            do
            {
                line = reader.readLine();
                if (line != null)
                    zt.setText(zt.getText() + "\n[" + zt.getLineCount() + "] " + line + " point(s)");
            } while (line != null);
            reader.close();
        }
        catch (Exception e)
        {
        }
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        Object source = ae.getSource();
        voix.stop();

        // Return button
        if (source.equals(quit))
        {
            this.zt.setText(""); // réinitialiser le texte
            Scores.actionOnClose();

            String text = "Retour";
            voix.playText(text);

            Utils.scoresFrame = null;
            this.dispose();
        }
    }

    /**
     * Action on close.
     */
    private static void actionOnClose()
    {
        Utils.scoresFrame = null;
    }

    /*
     * (non-Javadoc)
     * @see jeu.base.FenetreSimple#init()
     */
    @Override
    public void init()
    {}

    /*
     * (non-Javadoc)
     * @see jeu.base.FenetreSimple#changeColor()
     */
    @Override
    public void changeColor()
    {
        this.changeColorPanel((JPanel) this.contentPane);
        Utils.applyAppearance(this.zt);
        Utils.applyAppearance(this.contentPane);
    }

    /*
     * (non-Javadoc)
     * @see jeu.base.FenetreSimple#changeSize()
     */
    @Override
    public void changeSize()
    {
        this.changeSizePanel((JPanel) this.contentPane);
        Utils.applyAppearance(this.zt);
    }

    /*
     * (non-Javadoc)
     * @see devintAPI.FenetreAbstraite#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        voix.stop();
        super.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            voix.stop();
            Utils.scoresFrame = null;
        }
        if (e.getKeyCode() == KeyEvent.VK_F2)
        {
            voix.stop();
        }
    }

}
