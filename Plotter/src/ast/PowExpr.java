package ast;

import java.util.ArrayList;
import java.util.List;

public class PowExpr extends Expr {

	private Expr base;
	private Expr pow;

	public PowExpr(Expr base, Expr pow) {
		this.base = base;
		this.pow = pow;
	}

	@Override
	public double value(double x) {
		return Math.pow(base.value(x), pow.value(x));
	}

	@Override
	public List<String> collectErrors(List<String> e) {
		e.addAll(base.collectErrors(new ArrayList<String>()));
		e.addAll(pow.collectErrors(new ArrayList<String>()));
		return e;
	}
	
	public Expr derivate() {
		// TODO: implement
		return null;
	}


	public String toString() {
		return base.toString() + "^(" + pow.toString()+")";
	}

}
