package sudoku;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javafx.scene.control.TextField;

public class OneCharTextField extends TextField{
	
	private String regex;
	
	/**
	 * Creates a TextField that only accepts one character. The allowed characters are specified as a regular expression.
	 * 
	 * @param rules
	 * 				The regular expression that specifies the allowed characters
	 * 
	 * @throws PatternSyntaxException
	 * 				If the regular expression is not valid
	 */
	public OneCharTextField(String regex) throws PatternSyntaxException {
		super();
		this.regex=regex;
		Pattern.matches(regex, "0");
	}
	
	
	public void replaceText(int start, int end, String text) {
		if (matches(text)) {
			super.replaceText(start, end, text);			
		}
		super.end();
	}

	public void replaceSelection(String text) {
		if(matches(text)) {
			super.replaceSelection(text);
		}
		super.end();
	}
	
	private boolean matches(String text) {
		return text.isEmpty() || (getText().length() < 1) && text.matches(regex);
	}
}
