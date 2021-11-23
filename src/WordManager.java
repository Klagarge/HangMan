import java.text.Normalizer;
import java.io.*;
import java.util.ArrayList;
import java.net.URL;
// @Klagarge
/**
 * Class pour la gestion de mots
 * @author Rémi Heredero
 * @version 1.0.0
 */

public class WordManager {
    private String secretWord = "";
    public String userWord = "";

    void askSecretWord(){
        secretWord = randomWord();
        secretWord = stripAccents(secretWord);
        secretWord = secretWord.toLowerCase();
        userWord = "";

        for (int i = 0; i < secretWord.length(); i++) {
            userWord += '*';
        }
    }
    /**
     * Check si la lettre en paramètre est dans le secretWord
     * Modifie le userWord pour afficher la lettre trouvé à tous les emplacements bon
    * @param   letterToCheck   lettre à vérifier
    * @return  Retourne true si la lettre est dans le mot
    */
    boolean checkLetter(char letterToCheck){
        
        boolean letterPresent = false;
        for (int i = 0; i < secretWord.length(); i++) {
            if(letterToCheck == secretWord.charAt(i)){
                letterPresent = true;
                userWord = userWord.substring(0, i) + letterToCheck + userWord.substring(i+1);
            }
        }
        return letterPresent;
    }
    /**
     * Check si le userWord = le secretWord
     * @return true si les userWord et le secetWord sont strictement égaux
     */
    boolean isWordComplete(){
        boolean complete = false;
        if (secretWord.equals(userWord)) {
            complete = true;
            Dialogs.displayMessage("Victory !!");
        }
        return complete;


    }

    /**
     * Afficher un message passé en paramètre et le mot qu'il fallait deviner
     * @param msg Message à transmettre pour la défaitte.
     */
    void lost(String msg){
        String s = msg;
        s += "\n\nThe good word was: ";
        s += secretWord;
        Dialogs.displayMessage(s);
    }

    /**
     * Enlève les accent d'une chaîne de caractère.
     * @author Mudry Pierre-André
     * @param s Chaîne de caractère à transmettre avec (ou sans) accents.
     * @return Chaîne sans accent
     */
    public static String stripAccents(String s){
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    /**
     * Génère un mot aléatoire en fonction d'une difficulté choisie.
     * Créé un boîte de dialogue pour inviter l'utilisateur à indiquer le niveau de difficulté désiré
     * Choisit dans une liste de mot pré-établi selon le niveau: 
     * <ul><li> beginner:   liste de 19 mots extrêmement courant
     * <li> easy:       liste de 579 mots très courant
     * <li> medium:     liste de 4'872 mots courant
     * <li> difficult:  liste de 23'371 mots rare
     * <li> hardcore:   liste de 108'034 mots très rare </ul>
     * <p>Si pas de niveau choisit, choisit un mot aléatoirement parmis une liste de 331'782 mots
     * @return Retourne le mot généré aléatoirement selon la difficulté choisie
     */
    private String randomWord() {
        String askLevel = "";
        askLevel += "Please choose your level \n";
        askLevel += "('b' for beginner) \n";
        askLevel += "('e' for easy) \n";
        askLevel += "('m' for medium) \n";
        askLevel += "('d' for difficult) \n";
        askLevel += "('h' for hardcore)";
        char level = Dialogs.getChar(askLevel);
        String s = "";
        String[] word = loadList("words/mots.csv"); // 331'782 mots

        switch (level) {
            case 'b':
                word = loadList("words/mots_beginner.csv"); // 59 mots
                break;

            case 'e':
                word = loadList("words/mots_easy.csv"); // 579 mots
                break;

            case 'm':
                word = loadList("words/mots_medium.csv"); // 4'872 mots
                break;
                
            case 'd':
                word = loadList("words/mots_difficult.csv"); // 23'371 mots
                break;

            case 'h':
                word = loadList("words/mots_hardcore.csv"); // 108'034 mots
                break;

            default:
                break;
        }
        s = word[(int)(Math.random()*word.length)];
        System.out.println(s); // afficher le mot secret
        return s;
    }

    /**
     * Charge un fichier (csv ou txt) et met les ligne dans un tableau de String
     * ! le ficier doit être en UTF-8
     * @author Mudry Pierre-André
     * @param filePath chemin d'accès au fichier doit se trouver dans le src
     * @return Tableau de String avec toutes les lignes du fichier d'entrée
     */
    private String[] loadList(String filePath) {
        String[] wordList;
        try {
            URL url = this.getClass().getClassLoader().getResource(filePath);
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
          ArrayList < String > al = new ArrayList < String > ();
          while (bf.ready()) {
            String[] letterToCheck = bf.readLine().split(";");
            al.add(letterToCheck[0]);
          }
          wordList = al.stream().toArray(String[]::new);
          System.out.println("[Dictionary loaded with " + wordList.length + " words]");
          bf.close();
          return wordList;
        } catch(Exception e) {
          e.printStackTrace();
          return null;
        }
    }
}