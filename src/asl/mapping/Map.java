package mapping;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import jason.asSemantics.Agent;

public class Map {
	
	private static int height;
	private static int width;
	
	private static CountDownLatch latch = new CountDownLatch(2);
	
	private static List<MapEntry> map = new ArrayList<MapEntry>();
	
	private static List<Pair> explore_points = new ArrayList<Pair>();
	
	private static List<Agent> agents = new ArrayList<Agent>();
	private static List<Pair> agent_loc = new ArrayList<Pair>();
	
	public static void MapInit(int w, int h, int range) {
		Pair base_loc = new Pair(0,0);
		MapEntry base_entry = new MapEntry(base_loc, 's');
		map.add(base_entry);
		height = h-1;
		width = w-1;
		genExplorePoints(range);
	}
	
	private static void genExplorePoints(int range) {
		System.out.println("range :"+range);
		for(int i=0; i<height-range;i+=range*2) {
			for(int j=0; j<width-range;j+=range*2) {
				System.out.println("i :"+i+"j: "+j);
				Pair a = new Pair(i,j);
				Pair b = new Pair(i+range,j+range);
				explore_points.add(a);
				explore_points.add(b);
			}
		}
	}
	
	public static List getExplorePoints() { 
		return explore_points;
	}
	
	public static Pair getNextExplorePoint(Agent ag) {
		Pair nearest = getNearestLoc(ag);
		explore_points.remove(nearest);
		return nearest;
	}
	
	public static void addExplorePoint(Pair loc) {
		explore_points.add(loc);
	}
	
	public static int getHeight() {
		return height;
	}
	public static int getWidth() {
		return width;
	}
	
	public static void newEntry(int x, int y, char c) {
		if(x > width) {x = x-width-1;}
		if(y > height) {y = y-height-1;}
		if(x < 0) { x = x+width+1;}
		if(y < 0) { y = y+height+1;}
		for (MapEntry loc : map) {
			if(loc.getLoc().getX() == x && loc.getLoc().getY() == y) {
				return;
			}
		}
		Pair new_loc = new Pair(x,y);
		MapEntry new_entry = new MapEntry(new_loc, c);
		map.add(new_entry);
	}
	
	public static int newEntry(int x, int y, char c, int qty) {
		if(x > width) {x = x-width-1;}
		if(y > height) {y = y-height-1;}
		if(x < 0) { x = x+width+1;}
		if(y < 0) { y = y+height+1;}
		for (MapEntry loc : map) {
			if(loc.getLoc().getX() == x && loc.getLoc().getY() == y && loc.getContent()==c) {
				return -1;
			}
		}
		Pair new_loc = new Pair(x,y);
		MapEntry new_entry = new MapEntry(new_loc, c, qty);
		map.add(new_entry);
		return 1;
	}
	
	public static void newAgent(Agent ag) {
		if (!(agents.contains(ag))) {
			agents.add(ag);
			Pair start = new Pair(0,0);
			agent_loc.add(start);
		}
	}
	
	public static int getAgentID(Agent ag) {
		if(agents.contains(ag)) {
			return agents.indexOf(ag);
		}
		else {
			return -1;
		}
	}
	
	public static Pair getAgentLoc(Agent ag) {
		if(agents.contains(ag)) {
			return agent_loc.get(agents.indexOf(ag));
		}
		else {
			return null;
		}
	}
	
	public static void newAgentLocation(int x, int y, Agent ag) {
		if(agents.contains(ag)) {	
			Pair curr_loc = agent_loc.get(agents.indexOf(ag));
			x = x+curr_loc.getX();
			y = y+curr_loc.getY();
			if(x > width) {x = x-width-1;}
			if(y > height) {y = y-height-1;}
			if(x < 0) { x = x+width+1;}
			if(y < 0) { y = y+height+1;}
			Pair new_loc = new Pair(x,y);
			agent_loc.set(agents.indexOf(ag), new_loc);
		}
		else {
			System.console().printf("no agent to update");
		}
	}
	
	public static MapEntry getNearest(Agent ag, char rs) {
		Pair a = agent_loc.get(agents.indexOf(ag));
		MapEntry nearest = new MapEntry(new Pair(100,100), 'n');
		for (MapEntry loc : map) {
			if(loc.getContent() == rs && loc.getQty() > 0) {
				if(Pair.getSize(findPath(a,loc.getLoc())) < Pair.getSize(findPath(a,nearest.getLoc()))) {
					nearest = loc;
				}
			}
		}
		return nearest;
	}
	
	private static Pair getNearestLoc(Agent ag) {
		Pair a = agent_loc.get(agents.indexOf(ag));
		Pair nearest = new Pair(100,100);
		for (Pair loc : explore_points) {
				if(Pair.getSize(findPath(a,loc)) < Pair.getSize(findPath(a,nearest))) {
					nearest = loc;
				}
		}
		return nearest;
	}
	
	public static Pair findPath(Pair start, Pair end) {
		
		Pair patha, pathb, pathc, pathd;
		
		if(Pair.getSize(start)<Pair.getSize(end)) {
			patha = new Pair(end.getX()-start.getX(),end.getY()-start.getY());
			
			pathb = new Pair(-start.getX()-(width+1-end.getX()), end.getY()-start.getY());
			
			pathc = new Pair(end.getX()-start.getX(), -start.getY()-(height+1-end.getY()));
			
			pathd = new Pair(-start.getX()-(width+1-end.getX()), -start.getY()-(height+1-end.getY()));
			
		}
		else {
			patha = new Pair(end.getX()-start.getX(),end.getY()-start.getY());
			
			pathb = new Pair((width+1-start.getX())+end.getX(), end.getY()-start.getY());
			
			pathc = new Pair(end.getX()-start.getX(),(height+1-start.getY())+end.getY());
			
			pathd = new Pair((width+1-start.getX())+end.getX(), (height+1-start.getY())+end.getY());
			
		}

		
		if(Pair.getSize(patha)<Pair.getSize(pathb) && Pair.getSize(patha)<Pair.getSize(pathc) && Pair.getSize(patha)<Pair.getSize(pathd)) {
			return patha;
		}
		if(Pair.getSize(pathb)<Pair.getSize(patha) && Pair.getSize(pathb)<Pair.getSize(pathc) && Pair.getSize(pathb)<Pair.getSize(pathd)) {
			return pathb;
		}
		if(Pair.getSize(pathc)<Pair.getSize(pathb) && Pair.getSize(pathc)<Pair.getSize(patha) && Pair.getSize(pathc)<Pair.getSize(pathd)) {
			return pathc;
		}
		return pathd;
	}
	
	public static void updateResource(Pair res_loc, int qty) {
		
		for (MapEntry loc : map) {
			if (Pair.equals(loc.getLoc(), res_loc)) {
				loc.setQty(qty);
			}
		}
	}
	
	public static int checkResource(Pair res_loc) {
		for (MapEntry loc : map) {
			if (Pair.equals(loc.getLoc(), res_loc)) {
				return loc.getQty();
			}
		}
		return -1;
	}
	
	public static List<MapEntry> getResources(){
		List<MapEntry> resources = new ArrayList<MapEntry>();
		
		for (MapEntry loc : map) {
			if (loc.getContent() == 'G' || loc.getContent() == 'D') {
				resources.add(loc);
			}
		}
		return resources;
	}
	
	public static List<Pair> getAgentLocs(){
		return agent_loc;
	}
}
