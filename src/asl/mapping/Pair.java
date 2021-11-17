package mapping;

public class Pair {
	private int x;
	private int y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public static Pair add(Pair p, Pair q) {
		return new Pair(p.x+q.x,p.y+q.y);
	}
	
	public static int getDistance(Pair p, Pair q) {
		return Math.abs((p.x-q.x)+(p.y-q.y));
	}
	
	public static boolean equals(Pair p, Pair q) {
		if((p.getX() == q.getX()) && (p.getY() == q.getY())) {
			return true;
		}
		return false;
	}
}
