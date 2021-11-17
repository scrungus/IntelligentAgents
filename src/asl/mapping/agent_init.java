// Internal action code for project ia_submission

package mapping;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class agent_init extends DefaultInternalAction {
	
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.agent_init'");
        Map.newAgent(ts.getAg());
        ts.getAg().getLogger().info("Agent created with ID "+Map.getAgentID(ts.getAg())+" and location ("+Map.getAgentLoc(ts.getAg()).getX()+","+Map.getAgentLoc(ts.getAg()).getY()+")");
        // everything ok, so returns true
        return true;
    }
}
