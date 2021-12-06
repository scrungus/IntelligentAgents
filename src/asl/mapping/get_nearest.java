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
        char t = 'b';
        try {
        	Atom s = ((Atom)args[0]);
            if(s != null) {t = s.toString().charAt(0);}
        }finally{}
        

        char rs = ((Atom)args[3]).toString().charAt(0);
   
        MapEntry nearest = Map.getNearest(ts.getAg(),rs);
        if(nearest.getContent() == 'n') {
        	Pair loc = Map.getAgentLoc(ts.getAg());
        	return un.unifies(new NumberTermImpl(Map.getWidth()+1), args[1]) && un.unifies(new NumberTermImpl(Map.getHeight()+1), args[2]);
        }
        else if (t == 'a') {
        	ts.getAg().getLogger().info("returning absolute");
        	return un.unifies(new NumberTermImpl(nearest.getLoc().getX()), args[1]) && un.unifies(new NumberTermImpl(nearest.getLoc().getY()), args[2]);
        }
        else {
        	ts.getAg().getLogger().info("returning relative");
        	Pair path = Map.findPath(Map.getAgentLoc(ts.getAg()), nearest.getLoc());
        	return un.unifies(new NumberTermImpl(path.getX()), args[1]) && un.unifies(new NumberTermImpl(path.getY()), args[2]);
        }
        
    }
}
