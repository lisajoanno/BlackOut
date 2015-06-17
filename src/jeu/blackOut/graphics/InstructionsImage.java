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

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import jeu.base.FenetreSimple;
import jeu.blackOut.utils.Utils;
import jeu.blackOut.utils.instructions.InstructionImageWindowsAdapter;
import jeu.blackOut.utils.instructions.InstructionsImageText;

/**
 * The Class InstructionsImage.
 */
public class InstructionsImage extends FenetreSimple implements ActionListener {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The content pane (frame). */
    private Container contentPane;

    /** The south panel. */
    private JPanel centerPanel, northPanel, westPanel, southPanel;

    /** The image number. */
    private int imageNumber;

    /** The text. */
    private JTextArea text;

    /** The next. */
    private JButton previous, next;

    /** The hauteur. */
    int largeur, hauteur;

    /** The picture. */
    private JLabel picture;

    /** The quit. */
    private JButton quit;

    /** The title. */
    private JLabel title;

    private String aideF1Exemple = "../ressources/sons/F1/aideF1Exemple.wav";

    @Override
    protected String wavRegleJeu()
    {
        return aideF1Exemple;
    }

    /**
     * Instantiates a new instructions image.
     */
    public InstructionsImage()
    {
        super("Instructions");
        voix.stop();
        voix.playWav(aideF1Exemple);

        this.imageNumber = 0;

        this.contentPane = this.getContentPane();

        largeur = getToolkit().getScreenSize().width;
        hauteur = getToolkit().getScreenSize().height;

        this.setFrame();

        this.launchVoice();
    }

    /**
     * Launch voice.
     */
    private void launchVoice()
    {
        voix.stop();
        String str = null;

        switch (imageNumber)
        {
            case 0:
                str = InstructionsImageText.INSTRU_TEXT_0;
                break;
            case 1:
                str = InstructionsImageText.INSTRU_TEXT_1;
                break;
            case 2:
                str = InstructionsImageText.INSTRU_TEXT_2;
                break;
        }

        this.voix.playText(str);
    }

    /**
     * Sets the frame.
     */
    private void setFrame()
    {
        this.contentPane.setLayout(new BorderLayout());
        Utils.applyAppearance(contentPane);

        // Action on close
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new InstructionImageWindowsAdapter(voix));
        this.northPanel = new JPanel();
        Utils.applyAppearance(this.northPanel);

        title = new JLabel("Exemple - (1/3)");
        Utils.applyAppearance(title);
        this.northPanel.add(title);

        this.contentPane.add(northPanel, BorderLayout.NORTH);

        this.southPanel = new JPanel();
        Utils.applyAppearance(this.southPanel);

        Box box = Box.createHorizontalBox();
        this.previous = new JButton("<");
        Utils.applyAppearance(previous);
        this.previous.addActionListener(this);
        box.add(previous);

        quit = new JButton("Retour");
        Utils.applyAppearance(quit);
        quit.addActionListener(this);
        box.add(quit);

        this.next = new JButton(">");
        Utils.applyAppearance(next);
        this.next.addActionListener(this);
        box.add(next);

        this.southPanel.add(box);

        this.contentPane.add(southPanel, BorderLayout.SOUTH);

        this.centerPanel = new JPanel();
        Utils.applyAppearance(centerPanel);
        ImageIcon icon = new ImageIcon("../ressources/images/0.jpg");
        this.picture = new JLabel(icon, JLabel.CENTER);
        picture.setBackground(Color.black);
        this.picture.setOpaque(true);
        this.centerPanel.add(picture);

        this.contentPane.add(this.centerPanel, BorderLayout.CENTER);

        this.westPanel = new JPanel();
        Utils.applyAppearance(this.westPanel);

        this.text = new JTextArea(InstructionsImageText.INSTRU_TEXT_0);
        this.text.setBounds(50, 50, largeur / 3, hauteur / 2);
        this.text.setWrapStyleWord(true);
        this.text.setLineWrap(true);
        this.text.setEditable(false);
        Utils.applyAppearance(text);
        this.westPanel.add(text);

        this.contentPane.add(this.westPanel, BorderLayout.WEST);

        this.actualizeButtons();

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
        this.changeColorPanel(this.northPanel);
        this.changeColorPanel(this.southPanel);
        this.changeColorPanel(this.westPanel);
        this.changeColorPanel(this.centerPanel);
        Utils.applyAppearance(this.next);
        Utils.applyAppearance(this.previous);
        Utils.applyAppearance(this.quit);

    }

    /*
     * (non-Javadoc)
     * @see jeu.base.FenetreSimple#changeSize()
     */
    @Override
    public void changeSize()
    {
        this.changeSizePanel(this.northPanel);
        this.changeSizePanel(this.southPanel);
        this.changeSizePanel(this.westPanel);
        this.changeSizePanel(this.centerPanel);
        Utils.applyAppearance(this.next);
        Utils.applyAppearance(this.previous);
        Utils.applyAppearance(this.quit);

    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.next)
        {
            this.changeRight();
            this.launchVoice();
            this.actualizeLabel();
            this.actualizeButtons();
        }
        else if (e.getSource() == this.previous)
        {
            this.changeLeft();
            this.launchVoice();
            this.actualizeLabel();
            this.actualizeButtons();

        }
        else if (e.getSource() == this.quit)
        {
            Utils.imgFrame = null;
            this.dispose();
        }

    }

    private void actualizeButtons()
    {
        int prev = this.imageNumber;
        int suiv = this.imageNumber + 2;

        prev = (prev == 0) ? 3 : prev;
        suiv = (suiv == 4) ? 1 : suiv;

        this.previous.setText("< (" + prev + "/3)");
        this.next.setText("(" + suiv + "/3) >");

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        voix.stop();
        super.keyPressed(e);

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            voix.stop();
            Utils.imgFrame = null;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            this.next.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            this.previous.doClick();
        }

    }

    /**
     * Actualize label.
     */
    private void actualizeLabel()
    {
        this.title.setText("Exemple - (" + (this.imageNumber + 1) + "/3)");
    }

    /**
     * Change right.
     */
    public void changeRight()
    {
        this.imageNumber++;

        if (imageNumber >= 3)
            imageNumber = 0;

        loadText();
        loadPicture();
    }

    /**
     * Change left.
     */
    public void changeLeft()
    {
        this.imageNumber--;

        if (imageNumber < 0)
            imageNumber = 2;

        loadText();
        loadPicture();
    }

    /**
     * Load text.
     */
    public void loadText()
    {
        String str = null;

        switch (imageNumber)
        {
            case 0:
                str = InstructionsImageText.INSTRU_TEXT_0;
                break;
            case 1:
                str = InstructionsImageText.INSTRU_TEXT_1;
                break;
            case 2:
                str = InstructionsImageText.INSTRU_TEXT_2;
                break;
        }

        this.text.setText(str);
    }

    /**
     * Load picture.
     */
    public void loadPicture()
    {
        this.centerPanel.removeAll();

        String str = "../ressources/images/" + Integer.toString(imageNumber) + ".jpg";
        this.picture.removeAll();

        this.picture = null;
        ImageIcon icon = new ImageIcon(str);
        this.picture = new JLabel(icon, JLabel.CENTER);

        this.picture.setOpaque(true);
        // this.picture.setPreferredSize(new Dimension(largeur, hauteur));

        // Utils.applyAppearance(picture);
        picture.setBackground(Color.black);

        this.centerPanel.add(picture);

        Utils.refresh(this.picture);
        Utils.refresh(this.centerPanel);

    }

}
