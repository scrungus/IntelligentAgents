// Internal action code for project ia_submission

package mapping;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class get_loc extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.get_loc'");
        System.out.println("e");
        
        Pair loc = Map.getAgentLoc(ts.getAg());

        // everything ok, so returns true
        return un.unifies(new NumberTermImpl(loc.getX()), args[0]) && un.unifies(new NumberTermImpl(loc.getY()), args[1]);
    }
}
