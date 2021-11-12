// Internal action code for project ia_submission

package mapping;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class log extends DefaultInternalAction {
	
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.log', args: "+args[0]+args[1]);
        // everything ok, so returns true
        
        Map.newEntry((int)((NumberTerm)args[0]).solve(),(int)((NumberTerm)args[1]).solve());
        Map.newAgentLocation((int)((NumberTerm)args[0]).solve(),(int)((NumberTerm)args[1]).solve(),ts.getAg());
        Pair loc = Map.getAgentLocation(ts.getAg());
        ts.getAg().getLogger().info("logged agent location : ("+loc.getX()+","+loc.getY()+")");
        return true;
    }
}
