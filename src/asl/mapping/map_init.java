// Internal action code for project ia_submission

package mapping;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class map_init extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.map_init'");
        
        Map.MapInit((int)((NumberTerm)args[0]).solve(), (int)((NumberTerm)args[1]).solve());
        ts.getAg().getLogger().info("Height : "+Map.getHeight()+", Width : "+Map.getWidth());
        return true;
    }
}
