// Internal action code for project ia_submission

package mapping;

import java.util.List;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class get_next_location extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.get_next_location'");
        
        Pair nearest = Map.getNextExplorePoint(ts.getAg());
        ts.getAg().getLogger().info("Agent given location ("+nearest.getX()+","+nearest.getY()+")");
        Pair agent = Map.getAgentLoc(ts.getAg());
        Pair path = Map.findPath(agent, nearest);

        return un.unifies(new NumberTermImpl(path.getX()), args[0]) && un.unifies(new NumberTermImpl(path.getY()), args[1]);
    }
}
