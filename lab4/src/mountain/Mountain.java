package mountain;

import java.util.*;

import fractal.Fractal;
import fractal.TurtleGraphics;

public class Mountain extends Fractal {

	private Point p1;
	private Point p2;
	private Point p3;
	private double deviation;
	private static Map<Side, Point> m;

	public Mountain(Point p1, Point p2, Point p3, double dev) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.deviation = dev;
		Mountain.m = new HashMap<Side, Point>();
	}

	public String getTitle() {
		return "Fraktalberg";
	}

	public void draw(TurtleGraphics g) {
		fractal(g, order, p1, p2, p3, deviation);
	}

	/* Privat rekursiv metod */
	private void fractal(TurtleGraphics g, int order, Point p1, Point p2, Point p3, double dev) {
		if (order == 0) {
			g.moveTo(p1.getX(), p1.getY());
			g.forwardTo(p2.getX(), p2.getY());
			g.forwardTo(p3.getX(), p3.getY());
			g.forwardTo(p1.getX(), p1.getY());
		} else {
			Point p4;
			Point p5;
			Point p6;

			Side s1 = new Side(p1, p2);

			if (m.containsKey(s1)) {
				p4 = m.remove(s1);
			} else {
				p4 = new Point((p1.getX() + p2.getX()) / 2,
						(int) Math.floor(((p1.getY() + p2.getY()) / 2 - RandomUtilities.randFunc(dev))));
				m.put(s1, p4);
			}

			Side s2 = new Side(p2, p3);
			if (m.containsKey(s2)) {
				p5 = m.remove(s2);
			} else {
				p5 = new Point((p3.getX() + p2.getX()) / 2,
						(int) Math.floor(((p3.getY() + p2.getY()) / 2 - RandomUtilities.randFunc(dev))));
				m.put(s2, p5);
			}

			Side s3 = new Side(p3, p1);
			if (m.containsKey(s3)) {
				p6 = m.remove(s3);
			} else {
				p6 = new Point((p1.getX() + p3.getX()) / 2,
						(int) Math.floor(((p1.getY() + p3.getY()) / 2 + RandomUtilities.randFunc(dev))));
				m.put(s3, p6);
			}

			fractal(g, order - 1, p1, p4, p6, dev / 2);
			fractal(g, order - 1, p2, p4, p5, dev / 2);
			fractal(g, order - 1, p3, p5, p6, dev / 2);
			fractal(g, order - 1, p4, p5, p6, dev / 2);
		}
	}

	private class Side {
		private Point p1, p2;

		private Side(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}

		public Point[] getPoints() {
			Point[] p = { p1, p2 };
			return p;
		}

		public int hashCode() {
			return p1.hashCode() + p2.hashCode();
		}

		public boolean equals(Object obj) {
			if (obj instanceof Side) {
				Side s= (Side) obj;
				
				Point[] p = s.getPoints();

				if (p[0].equals(p1) && p[1].equals(p2)) {
					return true;
				}
				if (p[0] == p2 && p[1] == p1) {
					return true;
				}

				return false;
			}
			return false;
		}

	}
}
