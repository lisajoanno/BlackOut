package jeu.blackOut.utils.scores;

import java.util.Comparator;

/**
 * This class will be an object from the score file.
 * Exemple : in score.txt, superlisa 7
 */
public class ObjectScore implements Comparable<ObjectScore> {
    
    /**
     * Methode pour pouvoir utiliser un comparator.
     */
    public static final Comparator<ObjectScore> COMP = new Comparator<ObjectScore>() {
        public int compare(ObjectScore os, ObjectScore os2) {
            return os2.score - os.score;
        }
    };
    
    /** The score */
    private int score;
    /** The pseudo */
    private String pseudo;

    public ObjectScore() {    
    }
    
    public ObjectScore(int s, String p) {
        this.setScore(s);
        this.setPseudo(p);
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @param pseudo the pseudo to set
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * The compareTo. Compares the scores.
     */
    @Override
    public int compareTo(ObjectScore arg0) {
        if (this.getScore() > arg0.getScore()) {
            return 1;
        } else if (this.getScore() < arg0.getScore()) {
            return -1;
        } else {
            return 0;
        }
    }
}
