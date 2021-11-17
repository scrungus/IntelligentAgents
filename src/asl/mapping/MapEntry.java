package mapping;

public class MapEntry {
	
	private Pair loc;
	private char content;
	private int qty;
	
	public MapEntry(Pair loc, char content) {
		this.loc = loc;
		this.content = content;
	}
	
	public MapEntry(Pair loc, char content,int qty) {
		this.loc = loc;
		this.content = content;
		this.qty = qty;
	}
	
	public Pair getLoc() {
		return loc;
	}
	
	public char getContent() {
		return content;
	}
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int q) {
		this.qty = q;
	}
}
