package ast;

import java.util.ArrayList;
import java.util.List;

public class DivExpr extends Expr {

	private Expr left;
	private Expr right;

	public DivExpr(Expr left, Expr right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public double value(double x) {
		if (right.value(x) == 0) {
			return left.value(x) / 0.00001;
		}
		return left.value(x) / right.value(x);
	}

	@Override
	public List<String> collectErrors(List<String> e) {
		e.addAll(left.collectErrors(new ArrayList<String>()));
		e.addAll(right.collectErrors(new ArrayList<String>()));
		return e;
	}
	
	public Expr derivate() {
		// TODO: implement
		return null;
	}


	public String toString() {
		return left.toString() + "/" + right.toString();
	}

}
