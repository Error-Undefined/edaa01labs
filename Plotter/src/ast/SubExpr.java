package ast;

import java.util.List;

public class SubExpr extends Expr {
	private Expr left;
	private Expr right;

	public SubExpr(Expr left, Expr right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public double value(double x) {
		return left.value(x) - right.value(x);
	}

	@Override
	public List<String> collectErrors(List<String> e) {
		e.addAll(left.collectErrors(e));
		e.addAll(right.collectErrors(e));
		return e;
	}
	
	public Expr derivate() {
		// TODO: implement
		return null;
	}


	public String toString() {
		return left.toString() + "-" + right.toString();
	}
}
