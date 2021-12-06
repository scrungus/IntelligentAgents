package mapping;
// Internal action code for project ia_submission

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;
import mapping.Map;

public class add_explore_point extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        
    	int x = (int)((NumberTerm)args[0]).solve();
        int y = (int)((NumberTerm)args[1]).solve();
        
        Pair loc = new Pair(x,y);
        Map.addExplorePoint(loc);

        // everything ok, so returns true
        return true;
    }
}
