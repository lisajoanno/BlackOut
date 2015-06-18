/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.utils.helps;

/**
 * An enum of all messages.
 */
public enum HelpMessages {

	/** The welcome. */
	WELCOME("Bienvenue dans le jeu Black Out"), /** The exit. */
 EXIT("Aurevoir"), /** The retry. */
 RETRY(
			"Réessayer"), 
 /** The back. */
 BACK("Retour"), 
 /** The save. */
 SAVE("Sauvegarder"), 
 /** The epimode. */
 EPIMODE("Mode épileptique");

	/** The name. */
	private String name = "";

	/**
	 * Instantiates a new help messages.
	 *
	 * @param name the name
	 */
	HelpMessages(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return name;
	}

}
