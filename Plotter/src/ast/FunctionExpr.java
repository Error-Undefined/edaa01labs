package ast;

import java.util.ArrayList;
import java.util.List;

public class FunctionExpr extends Expr {

	private Expr expr;
	private String function;

	private final String[] expressions = { "sin", "cos", "sqrt", "ln", "exp" };

	public FunctionExpr(Expr expr, String function) {
		this.function = function;
		this.expr = expr;
	}

	public double value(double x) {
		switch (function) {
		case "sin":
			return Math.sin(expr.value(x));
		case "cos":
			return Math.cos(expr.value(x));
		case "sqrt":
			return Math.sqrt(expr.value(x));
		case "ln":
			return Math.log(expr.value(x));
		case "exp":
			return Math.exp(expr.value(x));
		}
		return 1;
	}

	

	@Override
	public List<String> collectErrors(List<String> e) {
		e.addAll(expr.collectErrors(new ArrayList<String>()));
		for (String s : expressions) {
			if (s.equals(function.toLowerCase())) {
				return e;
			}
		}
		e.add("Function \"" + function + "\" not understood.");
		return e;
	}
	
	public Expr derivate() {
		// TODO: implement
		return null;
	}

	public String toString() {
		return function + "(" + expr.toString() + ")";
	}
}
