package ast;

import java.util.List;

public class ConstExpr extends Expr {

	private String constant;
	private static String[] defined = { "x", "pi", "e" };

	public ConstExpr(String token) {
		this.constant = token;
	}

	public double value(double x) {
		switch (constant.toUpperCase()) {
		case "X":
			return x;
		case "PI":
			return Math.PI;
		case "E":
			return Math.E;
		default:
			return 0;
		}

	}

	@Override
	public List<String> collectErrors(List<String> e) {
		for (String s : defined) {
			if (s.equals(constant.toLowerCase())) {
				return e;
			}
		}
		e.add("Constant \"" + constant + "\" not defined.");
		return e;
	}

	public Expr derivate() {
		// TODO: implement
		return null;
	}

	public String toString() {
		return constant.toLowerCase();
	}
}
