// Internal action code for project ia_submission

package mapping;

import java.util.List;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class log extends DefaultInternalAction {
	
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.log', args: "+args[0]+args[1]);
        // everything ok, so returns true
        
        int x = (int)((NumberTerm)args[0]).solve();
        int y = (int)((NumberTerm)args[1]).solve();
        Map.newAgentLocation(x,y,ts.getAg());
        Pair loc = Map.getAgentLocation(ts.getAg());
        //Map.newEntry(loc.getX(), loc.getY(), 'b');
        ts.getAg().getLogger().info("logged agent location : ("+loc.getX()+","+loc.getY()+")");
        ts.getAg().getLogger().info("Agent Locations : ");
        List<Pair> locs = Map.getAgentLocations();
        for (Pair l : locs) {
        	ts.getAg().getLogger().info("("+l.getX()+","+l.getY()+")");
        }
        return true;
    }
}
