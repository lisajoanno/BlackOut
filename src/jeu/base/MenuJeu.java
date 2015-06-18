/*
 * Projet DevInt 2014-2015 - BlackOut
 */

package jeu.base; 

import jeu.blackOut.graphics.BlackOut;
import devintAPI.MenuAbstrait;

public class MenuJeu extends MenuAbstrait {

	private static final long serialVersionUID = 1L;

	/** constructeur
	 * @param title : le nom du jeu 
	 */
	public MenuJeu(String title) {
		super(title);
	}

	/** renvoie le nom des options du menu
     * vous pouvez définir autant d'options que vous voulez
     **/
	protected String[] nomOptions() {
		String[] noms = {"Fenêtre simple","Jeu","Gestion du son","Fichier des scores", "Gestion d'image", "BlackOut", "Quitter"};
		return noms;
	}

	/** lance l'action associée au bouton
	 * la numérotation est celle du tableau renvoyé par nomOption
	 */
	protected void lancerOption(int i) {
		switch (i){  
		case 0 : new FenetreSimple("Fenêtre simple");break;
		case 1 : new Jeu(nomJeu);break;
		case 2 : new GestionSon("Gestion du son");break;
		case 3 : new FichierScore("Ecriture dans un fichier");break;
		case 5 : new BlackOut(); break;
		case 6 : System.exit(0);
		default: System.err.println("action non définie");
		}
	} 

	// renvoie le fichier wave contenant le message d'accueil
	// ces fichiers doivent être placés dans ressources/sons/
	protected  String wavAccueil() {
		return "../ressources/sons/accueil.wav";
	}

	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavRegleJeu() {
		return "../ressources/sons/accueil.wav";
	}
	
}
