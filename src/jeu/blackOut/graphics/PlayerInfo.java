/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import jeu.base.FenetreSimple;
import jeu.blackOut.utils.Utils;

/**
 * The Class PlayerInfo.
 */
public class PlayerInfo extends FenetreSimple implements ActionListener {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The next. */
    private JButton quit, next;

    /** The panels. */
    private JPanel northPanel, southPanel, centerPanel;

    /** The title. */
    private JLabel title;

    /** The zt. */
    private JTextArea zt;

    /** The content pane. */
    private Container contentPane;

    /**
     * Instantiates a new player info.
     */
    public PlayerInfo()
    {
        super("BlackOut");
        
        voix.stop();
        voix.playWav(aideF1Pseudo);

        // Action on close
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                PlayerInfo.actionOnClose();
            }
        });

        this.centerPanel = new JPanel();
        Utils.applyAppearance(centerPanel);
        this.northPanel = new JPanel();
        Utils.applyAppearance(northPanel);
        this.southPanel = new JPanel(new GridLayout(1, 0));
        this.contentPane = this.getContentPane();
        this.contentPane.setLayout(new BorderLayout());

        title = new JLabel("Saisissez votre pseudo :");
        zt = new JTextArea();
        zt.requestFocus();
        quit = new JButton("Retour");
        next = new JButton("Jouer");

        // title
        this.northPanel.add(title);
        // this.contentPane.add(title, BorderLayout.NORTH);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        Utils.applyAppearance(title);

        // text area
        zt.setWrapStyleWord(true);
        Utils.applyAppearance(zt);
        zt.setLineWrap(true);
        int height = 50;
        int width = 300;
        zt.setBounds(this.centerPanel.getWidth() / 2 - width / 2, this.centerPanel.getHeight() / 2
                - height / 2, width, height);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 5);
        zt.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        zt.addKeyListener(new KeyListener() {

            /* (non-Javadoc)
             * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
             */
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    e.consume();
                    next.doClick();
                }
                
                if (e.getKeyCode() == KeyEvent.VK_F1)
                {
                    voix.stop();
                    voix.playWav("../ressources/sons/F1/aideF1Pseudo.wav");
                }
                
                
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    e.consume();
                    quit.doClick();
                }
            }
            

            @Override
            public void keyReleased(KeyEvent arg0)
            {

            }

            @Override
            public void keyTyped(KeyEvent arg0)
            {

            }

        });
        centerPanel.add(zt);
        this.contentPane.add(centerPanel, BorderLayout.CENTER);

        // quit button
        Utils.applyAppearance(quit);
        quit.addActionListener(this);
        southPanel.add(quit);

        // next button
        Utils.applyAppearance(next);
        next.addActionListener(this);
        southPanel.add(next);

        this.contentPane.add(northPanel, BorderLayout.NORTH);
        this.contentPane.add(southPanel, BorderLayout.SOUTH);
        Utils.applyAppearance(contentPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        zt.requestFocus();
    }

    private String aideF1Pseudo = "../ressources/sons/F1/aideF1Pseudo.wav";
    
    @Override
    protected String wavRegleJeu() {
        return aideF1Pseudo;
    }
    
    public static void actionOnClose()
    {}

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        Object source = ae.getSource();

        // Return button
        if (source.equals(quit))
        {
            Settings.actionOnClose();

            String text = "Retour";
            voix.playText(text);

            this.dispose();
        }

        // Next button
        if (source.equals(next))
        {

            String text = "Commencez à jouer";
            voix.playText(text);

            if (Utils.gameLaunched)
            {
                JOptionPane.showMessageDialog(this, "Une partie est déjà lancée.");
                return;
            }
            if (zt.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this, "Veuillez saisir un pseudo.");
            }
            else
            {
                Utils.pseudo = zt.getText().replace("\t", "").replace("\n", "");
                this.dispose();
                Utils.blackOutFrame = new BlackOut();
            }

        }
    }

    @Override
    public void init()
    {}

    @Override
    public void changeColor()
    {
        this.changeColorPanel((JPanel) this.contentPane);

        this.changeColorPanel(this.northPanel);
        this.changeColorPanel(this.southPanel);
        this.changeColorPanel(this.centerPanel);
        Utils.applyAppearance(this.contentPane);
    }

    @Override
    public void changeSize()
    {
        this.changeSizePanel((JPanel) this.contentPane);
        this.changeSizePanel(this.northPanel);
        this.changeSizePanel(this.southPanel);
        this.changeSizePanel(this.centerPanel);

        Utils.applyAppearance(this.zt);
        Utils.applyAppearance(this.southPanel);
    }

}
