package ast;

import java.util.ArrayList;
import java.util.List;

public class AddExpr extends Expr {

	private Expr left;
	private Expr right;

	public AddExpr(Expr left, Expr right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public double value(double x) {
		return left.value(x) + right.value(x);
	}

	@Override
	public List<String> collectErrors(List<String> e) {
		e.addAll(left.collectErrors(new ArrayList<String>()));
		e.addAll(right.collectErrors(new ArrayList<String>()));
		return e;
	}
	
	public Expr derivate() {
		//TODO: implement
		return null;
	}

	public String toString() {
		return left.toString() + "+" + right.toString();
	}

}
