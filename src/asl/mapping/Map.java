package mapping;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import jason.asSemantics.Agent;

public class Map {
	
	private static int height;
	private static int width;
	
	private static List<MapEntry> map = new ArrayList<MapEntry>();
	
	private static List<Agent> agents = new ArrayList<Agent>();
	private static List<Pair> agent_loc = new ArrayList<Pair>();
	
	public static void MapInit(int w, int h) {
		Pair base_loc = new Pair(0,0);
		MapEntry base_entry = new MapEntry(base_loc, 's');
		map.add(base_entry);
		height = h-1;
		width = w-1;
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
		for (MapEntry loc : map) {
			if(loc.getLoc().getX() == x && loc.getLoc().getY() == y) {
				return;
			}
		}
		Pair new_loc = new Pair(x,y);
		MapEntry new_entry = new MapEntry(new_loc, c);
		map.add(new_entry);
	}
	
	public static void newEntry(int x, int y, char c, int qty) {
		if(x > width) {x = x-width-1;}
		if(y > height) {y = y-height-1;}
		for (MapEntry loc : map) {
			if(loc.getLoc().getX() == x && loc.getLoc().getY() == y && loc.getContent() == 'g') {
				return;
			}
		}
		Pair new_loc = new Pair(x,y);
		MapEntry new_entry = new MapEntry(new_loc, c, qty);
		map.add(new_entry);
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
			Pair new_loc = new Pair(x,y);
			agent_loc.set(agents.indexOf(ag), new_loc);
		}
		else {
			System.console().printf("no agent to update");
		}
	}
	
	public static Pair getAgentLocation(Agent ag) {
		return agent_loc.get(agents.indexOf(ag));
	}
	
	public static MapEntry getNearest(Agent ag) {
		Pair a = agent_loc.get(agents.indexOf(ag));
		MapEntry nearest = new MapEntry(new Pair(100,100), 'n');
		for (MapEntry loc : map) {
			if(loc.getContent() == 'g' && loc.getQty() > 0) {
				if(Pair.getDistanceBetween(loc.getLoc(), a) < Pair.getDistanceBetween(nearest.getLoc(), a)) {
					nearest = loc;
				}
			}
		}
		return nearest;
	}
	
	public static Pair findPath(Pair start, Pair end) {
		
		Pair patha = new Pair(end.getX()-start.getX(),end.getY()-start.getY());
		
		Pair pathb = new Pair(-start.getX() - (width-end.getX()), -start.getY() - (height-end.getY()));
		
		if(Pair.getSize(patha)<Pair.getSize(pathb)) {
			return patha;
		}
		return pathb;
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
			if (loc.getContent() == 'g') {
				resources.add(loc);
			}
		}
		return resources;
	}
	
	public static List<Pair> getAgentLocations(){
		return agent_loc;
	}
}
