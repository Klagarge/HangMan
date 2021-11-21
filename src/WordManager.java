import java.text.Normalizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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

    boolean checkLetter(char c){
        boolean letterPresent = false;
        for (int i = 0; i < secretWord.length(); i++) {
            if(c == secretWord.charAt(i)){
                letterPresent = true;
                userWord = userWord.substring(0, i) + c + userWord.substring(i+1);
            }
        }
        return letterPresent;
    }

    boolean isWordComplete(){
        boolean complete = false;
        if (secretWord.equals(userWord)) {
            complete = true;
            Dialogs.displayMessage("Victory !!");
        }
        return complete;


    }

    void lost(String msg){
        String s = msg;
        s += "\n\nThe good word was: ";
        s += secretWord;
        Dialogs.displayMessage(s);
    }

    public static String stripAccents(String s){
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    private String randomWord() {
        String askLevel = "";
        askLevel += "Please choose your level \n";
        askLevel += "('e' for easy) \n";
        askLevel += "('m' for medium) \n";
        askLevel += "('d' for difficult)";
        char level = Dialogs.getChar(askLevel);
        int nbrLettreMin = 4;
        int nbrLettreMax = Integer.MAX_VALUE;
        String s = "";
        String[] word = loadList("C:/Users/remi/OneDrive/Documents/Cours/05-HEVS/S1fb/informatic/labo/vscode/Labo/src/lab6/mots.csv");

        switch (level) {
            case 'e':
                nbrLettreMin = 4;
                nbrLettreMax = 8;
                break;

            case 'm':
                nbrLettreMin = 9;
                nbrLettreMax = 16;
                break;
                
            case 'd':
                nbrLettreMin = 16;
                nbrLettreMax = Integer.MAX_VALUE;
                break;

            default:
                break;
        }
        int lg;
        do {
            int nbr = (int)(Math.random()*word.length);
            s = word[nbr];
            lg = s.length();
        } while (lg < nbrLettreMin || lg > nbrLettreMax);
        // System.out.println(s); // afficher le mot secret
        return s;
    }

    private String[] loadList(String filePath) {
        String[] wordList;
        try {
          BufferedReader bf = new BufferedReader(new FileReader(filePath));
          ArrayList < String > al = new ArrayList < String > ();
          while (bf.ready()) {
            String[] c = bf.readLine().split(";");
            al.add(c[0]);
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
