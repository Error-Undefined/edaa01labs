package syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenScanner {
private final Pattern pattern;
	
	public TokenScanner() {
		// Combine regular expression using or (|)
		StringJoiner tokenPatterns = new StringJoiner("|");
		
		// Identifiers
		tokenPatterns.add("[a-zA-Z][a-zA-Z0-9]*");
		
		// Numbers
		tokenPatterns.add("[0-9]+(\\.[0-9]+)?");
		
		// Operators
		tokenPatterns.add("\\+");
		tokenPatterns.add("\\-");
		tokenPatterns.add("\\*");
		tokenPatterns.add("\\/");
		tokenPatterns.add("\\^");

		// Parentheses
		tokenPatterns.add("\\(");
		tokenPatterns.add("\\)");
		
		// Whitespace -- ignore
		tokenPatterns.add("\\s+");
		
		// Compile combined regular expression
		pattern = Pattern.compile(tokenPatterns.toString());
	}
	
	public List<String> scan(String input) throws ScannerException {
		List<String> tokens = new ArrayList<>();
		Matcher m = pattern.matcher(input);

		int lastEnd = 0;
		while (m.find()) {
			String token = m.group();
			// A part was not matched by the regular expression
			if (lastEnd != m.start()) {
				error(input.substring(lastEnd, m.start()));
			}
			// Ignore whitespace
			if (!isWhitespaceToken(token)) {
				tokens.add(token);
			}
			lastEnd = m.end();
		}
		
		// Look for unmatched trailing characters
		if (lastEnd != input.length()) {
			error(input.substring(lastEnd));
		}

		return tokens;
	}
	
	private boolean isWhitespaceToken(String t) {
		return t.trim().isEmpty();
	}
	
	private void error(String unmatchedInput) throws ScannerException {
		throw new ScannerException("Could not match: " + unmatchedInput);
	}
	
	public static class ScannerException extends Exception {
		private static final long serialVersionUID = 1L;
		public ScannerException(String message) {
			super(message);
		}
	}
}
