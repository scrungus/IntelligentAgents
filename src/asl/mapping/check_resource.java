// Internal action code for project ia_submission

package mapping;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class check_resource extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.check_resource'");
        
        Pair t = Map.getAgentLocation(ts.getAg());
        ts.getAg().getLogger().info(" at location : ("+t.getX()+","+t.getY()+")");
        int q = Map.checkResource(Map.getAgentLocation(ts.getAg()));
        ts.getAg().getLogger().info("check_resource : q: "+q);
        return un.unifies(new NumberTermImpl(q), args[0]);
    }
}
