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
        askLevel += "('b' for beginner) \n";
        askLevel += "('e' for easy) \n";
        askLevel += "('m' for medium) \n";
        askLevel += "('d' for difficult) \n";
        askLevel += "('h' for hardcore)";
        char level = Dialogs.getChar(askLevel);
        String s = "";
        String[] word = loadList("src/mots.csv"); // 331'782 mots
        // C:/Users/remi/OneDrive/Documents/Cours/05-HEVS/S1fb/informatic/labo/vscode/HangMan/src/mots.csv

        switch (level) {
            case 'b':
                word = loadList("src/mots_beginner.csv"); // 19 mots
                break;

            case 'e':
                word = loadList("src/mots_easy.csv"); // 579 mots
                break;

            case 'm':
                word = loadList("src/mots_medium.csv"); // 4'872 mots
                break;
                
            case 'd':
                word = loadList("src/mots_difficult.csv"); // 23'371 mots
                break;

            case 'h':
                word = loadList("src/mots_hardcore.csv"); // 108'034 mots
                break;

            default:
                break;
        }
        s = word[(int)(Math.random()*word.length)];
        System.out.println(s); // afficher le mot secret
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