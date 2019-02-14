package ast;

import java.util.List;

public class NumExpr extends Expr {

	private double value;

	public NumExpr(double value) {
		this.value = value;
	}

	public double value(double x) {
		return value;
	}

	@Override
	public List<String> collectErrors(List<String> e) {
		return e;
	}

	public Expr derivate() {
		// TODO: implement
		return null;
	}

	public String toString() {
		return String.valueOf(value);
	}
}
