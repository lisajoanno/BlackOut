package jeu.blackOut.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LevelChange extends JDialog {

    private static final long serialVersionUID = 1L;
    private JLabel text, text2;
    private JPanel p;

    private KeyListener keyListener = new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e)
        {}

        @Override
        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                dispose();
            }

        }

        @Override
        public void keyPressed(KeyEvent e)
        {}
    };

    public LevelChange(String str1)
    {
        new myThread(this).start();
        this.setUndecorated(true);
        this.addKeyListener(keyListener);
        this.requestFocus();
        p = new JPanel(new BorderLayout());
        text = new JLabel(str1);
        text2 = new JLabel("(Appuyez sur Entrée)");
        text.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 70));
        text.setForeground(Color.BLUE);
        text2.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 70));
        text2.setForeground(Color.BLUE);
        this.p.add(text, BorderLayout.NORTH);
        this.p.add(text2, BorderLayout.CENTER);
        setContentPane(p);
        this.setModal(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    class myThread extends Thread {
        
        LevelChange lvl;
        
        public myThread(LevelChange lvl) {
            this.lvl = lvl;
        }

        @Override
        public void run()
        {
            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            this.lvl.dispose();
        }

    }

}
