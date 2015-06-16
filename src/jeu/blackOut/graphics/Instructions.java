/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import jeu.base.FenetreSimple;
import jeu.blackOut.utils.Utils;
import jeu.blackOut.utils.helps.Help;
import jeu.blackOut.utils.instructions.InstructionWindowsAdapter;

public class Instructions extends FenetreSimple implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JButton quit;

    private JLabel title;

    private JScrollPane sp;

    private JTextArea zt;

    private Container contentPane;

    public Instructions() {
        super("Instructions");
        
        voix.stop();
        voix.playWav(aideF1Instructions);
        
        // Action on close
        this.addWindowListener(new InstructionWindowsAdapter(voix));
        this.contentPane = this.getContentPane();
        this.contentPane.setLayout(new BorderLayout());

        title = new JLabel("Instructions");
        zt = new JTextArea(Utils.instructions);
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

        // quit button
        Utils.applyAppearance(quit);
        quit.addActionListener(this);
        this.contentPane.add(quit, BorderLayout.SOUTH);

        Utils.applyAppearance(contentPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        //voix.playText(Help.instructions);
    }

    
    private String aideF1Instructions = "../ressources/sons/F1/aideF1Instructions.wav";

    @Override
    protected String wavRegleJeu() {
        return aideF1Instructions;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();

        // Return button
        if (source.equals(quit)) {
            voix.stop();

            String text = "Retour";
            voix.playText(text);

            Utils.instructionsFrame = null;
            this.dispose();
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void changeColor() {
        this.changeColorPanel((JPanel) this.contentPane);
        Utils.applyAppearance(this.zt);
        Utils.applyAppearance(this.contentPane);
    }

    @Override
    public void changeSize() {
        this.changeSizePanel((JPanel) this.contentPane);
        Utils.applyAppearance(this.zt);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        voix.stop();
        super.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Utils.instructionsFrame = null;
        }
        if (e.getKeyCode() == KeyEvent.VK_F2) {
            voix.stop();
            voix.playText(Help.instructions);
        }
    }

}
