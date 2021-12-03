// Internal action code for project ia_submission

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class add_explore_point extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action '<PCK>.add_explore_point'");
        if (true) { // just to show how to throw another kind of exception
            throw new JasonException("not implemented!");
        }

        // everything ok, so returns true
        return true;
    }
}
