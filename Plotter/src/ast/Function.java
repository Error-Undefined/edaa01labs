package ast;

import java.util.ArrayList;
import java.util.List;

public class Function {
	private final Expr expr;

	public Function(Expr expr) {
		this.expr = expr;
	}

	public double value(double x) {
		return expr.value(x);
	}
	
	public double derivate(double x) {
		return 0;
	}

	
	public List<String> collectErrors() {
		return expr.collectErrors(new ArrayList<String>());
	}
	
	public String toString() {
		return expr.toString();
	}
}
