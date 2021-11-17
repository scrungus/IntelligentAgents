// Internal action code for project ia_submission

package mapping;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class get_nearest extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.get_nearest'");
        
        MapEntry nearest = Map.getNearest(ts.getAg());
        if(nearest.getContent() == 'n') {
        	Pair loc = Map.getAgentLocation(ts.getAg());
        	return un.unifies(new NumberTermImpl(-loc.getX()), args[0]) && un.unifies(new NumberTermImpl(-loc.getY()), args[1]);
        }
        else {
        	Pair path = Map.findPath(Map.getAgentLocation(ts.getAg()), nearest.getLoc());
        	return un.unifies(new NumberTermImpl(path.getX()), args[0]) && un.unifies(new NumberTermImpl(path.getY()), args[1]);
        }
        
    }
}
