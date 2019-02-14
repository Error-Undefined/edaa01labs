package gui;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField{
		
	public NumberTextField(){
		super();
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
		return text.matches("[0-9]|-|\\s");
	}
}
