/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.utils.scores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import jeu.blackOut.utils.Constants;

/**
 * A class allowing to save a score, or read a score in a "score.txt".
 * 
 */
public class SavedScore {

    public SavedScore() {

    }

    /**
     * Writes a new lign in score.txt,
     * 
     * @param toWrite
     *            , something like : "superlisa 7"
     */
    private void writeInScoreFile(String toWrite) {
        String fileName = Constants.RESOURCES_PATH + "score.txt";
        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(toWrite + "\n");

            bw.close();
        } catch (IOException e) {
        } catch (SecurityException e) {
        }
    }

    /**
     * Return a boolean following if the pseudo placed in parameters exists in
     * "score.txt" or not.
     * 
     * @param pseudo
     * @return
     */
    public boolean pseudoExists(String pseudo) {
        return (readScore(pseudo) == null) ? false : true;
    }

    /**
     * Reads "score.txt" and detect if the pseudo exists.
     * 
     * @param pseudo
     *            , something like : "superlisa"
     * @return if the pseudo exists : "superlisa 7" if not : null
     */
    @SuppressWarnings("resource")
    public String readScore(String pseudo) {
        File file = new File(Constants.RESOURCES_PATH + "score.txt");
        BufferedReader reader;
        String lign;
        try {
            reader = new BufferedReader(new FileReader(file));

            do {
                lign = reader.readLine();
                if (lign != null) {
                    if (getPseudoInLign(lign).equals(pseudo)) {
                        return lign;
                    }
                }
            } while (lign != null);
            reader.close();
        } catch (Exception e) {
        }
        // meaning the pseudo doesn't exist.
        return null;
    }

    /**
     * In a lign (not null), detects the "pseudo".
     * 
     * @param lign
     *            , like "superlisa 7"
     * @return "superlisa"
     */
    private String getPseudoInLign(String lign) {
        String p = "";
        for (int i = 0; i < lign.length(); i++) {
            if (lign.charAt(i) == (' ')) {
                p = lign.substring(0, i);
            }
        }
        return p;
    }

    /**
     * Writes a score when the player saves the game.
     * 
     * @param pseudo
     * @param score
     */
    public void writeScore(String pseudo, int score) {
        // System.out.println(" == pseudo exists ?" + pseudoExists(pseudo));
        // first detect if the pseudo already exists or not
        if (pseudoExists(pseudo)) {
            // the pseudo already exists
            rewriteScore(pseudo, score);
        } else {
            // the pseudo doesn't exists yet.
            writeNewScore(pseudo, score);
        }
    }

    /**
     * Writes a new lign in the score.txt.
     * 
     * @param newPseudo
     * @param newScore
     */
    private void writeNewScore(String newPseudo, int newScore) {
        String toWrite = newPseudo + " " + newScore;
        writeInScoreFile(toWrite);
    }

    /**
     * Detects the lign with the pseudo in score.txt and rewrites its score. (we
     * know the pseudo already exists in score.txt)
     * 
     * @param pseudo
     * @param score
     */
    private void rewriteScore(String pseudo, int score) {
        String fileName = Constants.RESOURCES_PATH + "score.txt";
        File file = new File(fileName);
        String lign;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            do {
                lign = reader.readLine();
                if (lign != null) {
                    if (getPseudoInLign(lign).equals(pseudo)) {
                        removeFromScoreFile(lign);
                    }
                }
            } while (lign != null);
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        writeNewScore(pseudo, score);
    }

    /**
     * Méthode utilisée pour savoir quel score correspond à un pseudo spécifié.
     * 
     * @param pseudo
     * @return 1 si le pseudo n'existe pas le score du pseudo s'il existe.
     */
    public int getScore(String pseudo) {
        if (!pseudoExists(pseudo)) {
            return 1;
        } else {
            return getScoreInLign(readScore(pseudo));
        }
    }

    /**
     * Reads "score.txt" and detects the score of the pseudo.
     * 
     * @param pseudo
     *            , something like : "superlisa 7"
     * @return if the pseudo exists : "7" if not : 1
     */
    private int getScoreInLign(String lign) {
        String p = "";
        for (int i = 0; i < lign.length(); i++) {
            if (lign.charAt(i) == (' ')) {
                p = lign.substring(i + 1, lign.length());
            }
        }
        int res = 0;
        try {
            res = Integer.parseInt(p);
        } catch (Exception e) {

        }
        return res;
    }

    /**
     * Enlève une ligne dans le ficier des scores.
     * 
     * @param lignNotTowrite
     */
    private void removeFromScoreFile(String lignNotTowrite) {
        // on créé un fichier temporaire
        String fileNameTemp = Constants.RESOURCES_PATH + "scoreTemp.txt";
        File fileTemp = new File(fileNameTemp);
        File trueFile = new File(Constants.RESOURCES_PATH + "score.txt");
        // on écrit sur ce fichier toutes les lignes sauf celle passée en param
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(trueFile));

            if (!fileTemp.exists()) {
                fileTemp.createNewFile();
            }

            FileWriter fw = new FileWriter(fileTemp.getAbsoluteFile());
            // pas
            // de
            // boolean
            // pour
            // écraser
            // puis
            // réécrire
            // le
            // fichier
            BufferedWriter bw = new BufferedWriter(fw);
            String lign = "";
            do {
                lign = reader.readLine();
                if (lign != null) {
                    if (!(lign.equals(lignNotTowrite))) {
                        bw.write(lign + "\n");
                    }
                }
            } while (lign != null);
            reader.close();
            bw.close();
        } catch (IOException e) {
        } catch (SecurityException e) {
        }
        rewriteAllScoreFile();
    }

    /**
     * Méthode qui écrase le fichier des scores et le réecrit en fonction du
     * fichier temporaire ("scoreTemp.txt") également dans le fichier resources.
     */
    private void rewriteAllScoreFile() {
        File file = new File(Constants.RESOURCES_PATH + "score.txt");
        BufferedReader reader;
        File fileTemp = new File(Constants.RESOURCES_PATH + "scoreTemp.txt");
        try {
            reader = new BufferedReader(new FileReader(fileTemp));

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            String lign;
            do {
                lign = reader.readLine();
                if (lign != null) {
                    bw.write(lign + "\n");
                }
            } while (lign != null);
            reader.close();
            bw.close();
        } catch (Exception e) {
        }
        fileTemp.delete();
        
    }

    /**
     * This method sorts "score.txt" by score, in a decreasing order.
     */
    public void sortScore() {
        // stocker tous les scores et pseudos dans une hashmap
        // la clé sera le score et la valeur sera le pseudo
        ArrayList<ObjectScore> listObjScore = new ArrayList<ObjectScore>();
        ObjectScore os;
        // on remplit la hm en parcourant toutes les lignes de score.txt
        File file = new File(Constants.RESOURCES_PATH + "score.txt");
        BufferedReader reader;
        String lign;
        try {
            reader = new BufferedReader(new FileReader(file));
            do {
                lign = reader.readLine();
                if (lign != null) {
                    // chaque ligne est ajouté à la liste
                    String thisPseudo = getPseudoInLign(lign);
                    int thisScore = getScoreInLign(lign);
                    os = new ObjectScore(thisScore, thisPseudo);
                    listObjScore.add(os);
                }
            } while (lign != null);
            reader.close();
        } catch (Exception e) {
        }
        // faire le tri grâce à la méthode de ObjectScore
        Collections.sort(listObjScore, ObjectScore.COMP);

        
        
        
        eraseFile();
        
        

        // on va réécrire score.txt donc on l'écrase
        // TODO boolean b = file.delete();
        // TODO System.out.println(b ? "DELETED" : "NOT DELETED");

        file = new File(Constants.RESOURCES_PATH + "score.txt");

        String line, pseudo;
        for (int i = 0; i < listObjScore.size(); i++) {
            try {
                pseudo = listObjScore.get(i).getPseudo();
                line = pseudo + " " + listObjScore.get(i).getScore();
                this.writeInScoreFile(line);
            } catch (Exception e) {
            }
        }
    }
    
    
    /**
     * 
     */
    public static void eraseFile() {
        String fileName = Constants.RESOURCES_PATH + "score.txt";
        File file = new File(fileName);

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("");
            bw.close();
        } catch (IOException e) {
        } catch (SecurityException e) {
        }
    }
}
