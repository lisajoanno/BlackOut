/*
 * Projet DevInt 2014-2015 - BlackOut
 */


package devintAPI;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;

import jeu.blackOut.utils.Constants;


/** Classe abstraite avec un Frame, une instance de SI_VOX pour parler et 
 * qui écoute les évènements clavier avec gestion des préférences.
 * Par défaut, un son est lu à l'activation de la fenêtre, 
 * on sort de la fenêtre par ESC et on obtient la règle du jeu par F1, l'aide par F2
 * 
 * @author helene
 *
 */
public abstract class FenetreAbstraite extends DevintFrameListener {
   
	private static final long serialVersionUID = 1L;

	/**
     * @param title : titre de la fenêtre
     */
    public FenetreAbstraite(String title) {
    	super(title);
       	// méthode init à implémenter, elle construit ce qui est dans le frame
       	init();
       	
       	// Antoine ajout
		try {
			this.setIconImage(ImageIO.read(new File(
					Constants.ICON_PATH)));
		} catch (Exception e) {
		}
       	
	     // visible
    	this.setVisible(true);
    	// a le focus
    	this.requestFocus();
    }

    /** méthode abstraite à implémenter 
     * pour définir ce qu'il y a dans le Frame
     */
    protected abstract void init();
    
    /** méthode abstraite à implémenter
     *  
     * @return le fichier wav contenant le message d'aide (activé par F2)
     */
    protected abstract String wavAide();

    //////////////////////////////////////////////
    // Gestion des évènements clavier
    /////////////////////////////////////////////
    public void keyPressed(KeyEvent e) {
    	// gestion de ESC, F1, F3 et F4 dans la classe mère (DevintFrameListener)
    	super.keyPressed(e);
    	// on ajoute la gestion de l'aide quand on presse F2

    }

	/**
	 * Pour modifier les couleurs de fond et de premier plan de la fenêtre
	 * Cette fonction est appelée à chaque fois que l'on presse F3 
	 * 
	 * Cette méthode doit être réécrite dans les classes filles 
	 * si cela n'a pas de sens pour votre jeu, vous la redéfinissez en la laissant vide
	 **/
	public abstract void changeColor() ;
	
	/**
	 * Pour modifier la police des textes à chaque fois que l'on presse F4 
	 * (ou la taille des images)
	 * 
	 * Cette méthode doit être réécrite dans les classes filles 
	 * si cela n'a pas de sens pour votre jeu, vous la redéfinissez en la laissant vide
	 **/
	public abstract void changeSize() ;
	
}

