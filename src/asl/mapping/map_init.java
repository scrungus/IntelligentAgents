// Internal action code for project ia_submission

package mapping;

import java.util.List;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class map_init extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.map_init'");
        
        Map.MapInit((int)((NumberTerm)args[0]).solve(), (int)((NumberTerm)args[1]).solve(),(int)((NumberTerm)args[2]).solve());
        ts.getAg().getLogger().info("Height : "+Map.getHeight()+", Width : "+Map.getWidth());
        
        ts.getAg().getLogger().info("List of Explore Points : ");
        List<Pair> resources = Map.getExplorePoints();
        
        for (Pair r : resources) {
        	ts.getAg().getLogger().info("("+r.getX()+","+r.getY()+")");
        }
        return true;
    }
}
