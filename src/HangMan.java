import java.awt.Color;

import hevs.graphics.FunGraphics;

public class HangMan {
    final int MAX_STEPS = 8;
    final int scale = 2;
    int current_step = 0;
    String inputLetter = "Inputed letter: ";
    String inputLetter1 = "";
    String inputLetter2 = "";
    WordManager word = new WordManager();
    FunGraphics man = new FunGraphics(300 * scale, 300 * scale, 0, 0, "Hangman", true);

    boolean play() {
        boolean party = true;
        char c = Dialogs.getChar("Please enter a new letter");
        inputLetter += c + " ";

        if (inputLetter.length()<=50) {
            inputLetter1 = inputLetter.substring(0, inputLetter.length());
        } else {
            inputLetter1 = inputLetter.substring(0, 50);
            inputLetter2 = "                    " + inputLetter.substring(51, inputLetter.length());
        }

        boolean validLetter = word.checkLetter(c);
        if (!validLetter) {
            current_step++;
        }
        updateGraphicsView();
        if (current_step >= MAX_STEPS) {
            party = false;
            word.lost("Sorry, you have lost ;(");

        }

        return party;
    }

    void updateGraphicsView() {
        man.clear();
        man.drawString(20 * scale, 60 * scale, "Word : " + word.userWord, Color.DARK_GRAY, 20*scale);
        man.drawString(20*scale, 260*scale, inputLetter1, Color.DARK_GRAY, 10*scale);
        man.drawString(20*scale, 270*scale, inputLetter2, Color.DARK_GRAY, 10*scale);
        for (int i = 1; i <= current_step; i++) {
            switch (i) { // dessin du pendu
            case 1:
                man.drawLine(110 * scale, 210 * scale, 120 * scale, 190 * scale);
                man.drawLine(130 * scale, 210 * scale, 120 * scale, 190 * scale);
                break;

            case 2:
                man.drawLine(120 * scale, 190 * scale, 120 * scale, 100 * scale);
                break;

            case 3:
                man.drawLine(120 * scale, 100 * scale, 180 * scale, 100 * scale);
                break;

            case 4:
                man.drawLine(180 * scale, 100 * scale, 180 * scale, 110 * scale);
                break;

            case 5:
                man.drawCircle(170 * scale, 110 * scale, 20 * scale);
                break;

            case 6:
                man.drawLine(180 * scale, 130 * scale, 180 * scale, 170 * scale);
                break;

            case 7:
                man.drawLine(180 * scale, 170 * scale, 170 * scale, 190 * scale);
                man.drawLine(180 * scale, 170 * scale, 190 * scale, 190 * scale);
                break;

            case 8:
                man.drawLine(170 * scale, 150 * scale, 190 * scale, 150 * scale);
                break;

            default:
                break;
            }
        }

    }
            
    public static void main(String[] args) {
        HangMan hang = new HangMan();
        while (true) {
            hang.word.askSecretWord();
            hang.inputLetter = "Inputed letter: ";
            hang.inputLetter1 = "";
            hang.inputLetter2 = "";
            hang.current_step = 0;
            hang.updateGraphicsView();
            while (hang.play() && !hang.word.isWordComplete()) {
                System.out.println(hang.word.userWord);
            }
            char answer = Dialogs.getChar("Do you want to play another party ? (y/n) ");
            if (answer != 'y' && answer != 'Y') {
                // System.out.println("Ok, see you later.");
                Dialogs.displayMessage("Ok, see you later.");
                System.exit(1);
            }
        }
    }
}