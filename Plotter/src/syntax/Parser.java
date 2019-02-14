package syntax;

import java.util.Iterator;
import java.util.List;

import ast.*;

/*
 * Parser for grammar:
 * Expr -> Term ("+"|"-" Term)*
 * Term -> Factor ("*"|"/" Factor)*
 * Factor -> Power ("^" Power)*
 * Power -> NUMBER | IDENTIFIER | "(" Expr ")" | IDENTIFIER "(" Expr ")"
 */
public class Parser {

	private final static String EOF_TOKEN = "$EOF";
	private Iterator<String> remainingTokens;
	private String token;

	public Function parse(List<String> tokens) throws ParseException {
		remainingTokens = tokens.iterator();
		token = remainingTokens.hasNext() ? remainingTokens.next() : EOF_TOKEN;
		Expr e = expr();
		return new Function(e);
	}

	private Expr expr() throws ParseException {
		Expr e = term();
		while (token.equals("+") || token.equals("-")) {
			if (token.equals("+")) {
				accept();
				Expr e2 = term();
				e = new AddExpr(e, e2);
			} else if (token.equals("-")) {
				accept();
				Expr e2 = term();
				e = new SubExpr(e, e2);
			}
		}
		return e;
	}

	private Expr term() throws ParseException {
		Expr t = power();
		while (token.equals("*") || token.equals("/")) {
			if (token.equals("*")) {
				accept();
				Expr t2 = power();
				t = new MultExpr(t, t2);
			} else if (token.equals("/")) {
				accept();
				Expr t2 = power();
				t = new DivExpr(t, t2);
			}
		}
		return t;
	}

	private Expr power() throws ParseException {
		Expr p = factor();
		while (token.equals("^")) {
			accept();
			Expr p2 = factor();
			p = new PowExpr(p, p2);
		}
		return p;
	}

	private Expr factor() throws ParseException {
		Expr e = null;
		if (Character.isDigit(token.charAt(0))) {
			if (isNumber()) { // Case number
				e = new NumExpr(Double.parseDouble(token));
				accept();
			}
		} else if (token.equals("(")) { // Case open paranthesis
			accept();
			e = expr();
			accept(")");
		} else { // Case constant, function or error
			String current = token;
			accept();
			if (token.equals("(")) { // Case function
				accept();
				e = new FunctionExpr(expr(), current);
				accept(")");
			} else {
				e = new ConstExpr(current);
			}
		}
		return e;
	}

	private void accept() {
		if (remainingTokens.hasNext()) {
			token = remainingTokens.next();
		} else {
			token = EOF_TOKEN;
		}
	}

	private void accept(String expected) throws ParseException {
		if (!token.equals(expected)) {
			throw new ParseException("Expected: " + expected + ", got: " + token);
		}
		accept();
	}

	private boolean isNumber() throws ParseException {
		try {
			Double.parseDouble(token);
			return true;
		} catch (NumberFormatException e) {
			throw new ParseException("Token " + token + " is not a valid number.");
		}
	}

	public static class ParseException extends Exception {
		private static final long serialVersionUID = 1L;

		public ParseException(String message) {
			super(message);
		}
	}
}
