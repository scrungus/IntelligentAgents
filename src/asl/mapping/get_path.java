// Internal action code for project ia_submission

package mapping;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class get_path extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.get_path'");
        
        int x = (int)((NumberTerm)args[0]).solve();
        int y = (int)((NumberTerm)args[1]).solve();
        
        Pair loc = new Pair(x,y);
        
    	Pair path = Map.findPath(Map.getAgentLoc(ts.getAg()), loc);
    	
    	return un.unifies(new NumberTermImpl(path.getX()), args[2]) && un.unifies(new NumberTermImpl(path.getY()), args[3]);
    }
}
