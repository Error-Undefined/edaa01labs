package ast;

import java.util.List;

public abstract class Expr {
	public abstract double value(double x);
	
	public abstract List<String> collectErrors(List<String> e);
	
	public abstract Expr derivate();
}
