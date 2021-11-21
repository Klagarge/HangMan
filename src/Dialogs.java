import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 * This class allows to display simple messages and have graphical interfaces to
 * enter words and characters.
 * 
 * @version 2.0
 * 
 */
public class Dialogs {
	/**
	 * This function open a dialog box to enter a hidden String. The dialog box
	 * asks for a String containing a minimum of one character.
	 * 
	 * @param message
	 *            The message displayed to ask for the hidden String
	 * @return The hidden String entered
	 */
	public static String getHiddenString(String message) {
		JPasswordField passwordField = new JPasswordField(10);
		JFrame frame = new JFrame(message);
	    // prompt the user to enter their name
	    JOptionPane.showMessageDialog(frame, passwordField, message, JOptionPane.PLAIN_MESSAGE);
		
		String s = new String(passwordField.getPassword());
		if (s.length() > 0)
			return s;
		else
			return getHiddenString("Enter at least one character");
	}

	/**
	 * This function open a dialog box to enter a character. This function
	 * accepts only one character.
	 * 
	 * @param message
	 *            The message asking for the character.
	 * @return The character entered.
	 */
	public static char getChar(String message) {
	    JFrame frame = new JFrame("Input a character please");
	    // prompt the user to enter their name
	    String s = JOptionPane.showInputDialog(frame, message);	    
	    
	    if(s == null){
	    	System.exit(-1);
	    }
	    
		if (s.length() == 1)
			return s.charAt(0);
		else
			return getChar("Just one character");
	}

	/**
	 * Open a dialog box to display a message.
	 * 
	 * @param message
	 *            The message to display.
	 */
	public static void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message, null, JOptionPane.PLAIN_MESSAGE);
	}

}
