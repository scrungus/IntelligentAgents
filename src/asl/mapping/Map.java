package mapping;

import java.util.ArrayList;
import java.util.List;

import jason.asSemantics.Agent;

public class Map {
	
	private static List<MapEntry> map = new ArrayList<MapEntry>();
	
	private static List<Agent> agents = new ArrayList<Agent>();
	private static List<Pair> agent_loc = new ArrayList<Pair>();
	
	public static void MapInit() {
		Pair base_loc = new Pair(0,0);
		MapEntry base_entry = new MapEntry(base_loc, 's');
		map.add(base_entry);
	}
	
	public static void newEntry(int x, int y) {
		Pair new_loc = new Pair(x,y);
		MapEntry new_entry = new MapEntry(new_loc, 'b');
		map.add(new_entry);
	}
	
	public static void newAgent(Agent ag) {
		if (!(agents.contains(ag))) {
			agents.add(ag);
			Pair start = new Pair(0,0);
			agent_loc.add(start);
		}
	}
	
	public static void newAgentLocation(int x, int y, Agent ag) {
		if(agents.contains(ag)) {
			Pair new_loc = new Pair(x,y);
			Pair curr_loc = agent_loc.get(agents.indexOf(ag));
			agent_loc.set(agents.indexOf(ag), Pair.add(curr_loc, new_loc));
		}
	}
	
	public static Pair getAgentLocation(Agent ag) {
		return agent_loc.get(agents.indexOf(ag));
	}
}
