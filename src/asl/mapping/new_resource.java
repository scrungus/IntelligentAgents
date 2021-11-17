// Internal action code for project ia_submission

package mapping;

import java.util.List;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class new_resource extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.new_resource'");
        
        int xdist = (int)((NumberTerm)args[0]).solve();
        int ydist = (int)((NumberTerm)args[1]).solve();
        char s = ((StringTerm)args[2]).getString().charAt(0);
        int q = (int)((NumberTerm)args[3]).solve();
        
        Pair ag = Map.getAgentLocation(ts.getAg());
        Map.newEntry(ag.getX()+xdist,ag.getY()+ydist,s,q);
        
        ts.getAg().getLogger().info("List of Resources : ");
        List<MapEntry> resources = Map.getResources();
        
        for (MapEntry r : resources) {
        	ts.getAg().getLogger().info(r.getContent()+" "+", loc ("+r.getLoc().getX()+","+r.getLoc().getY()+"), Qty:"+r.getQty());
        }
        return true;
    }
}
