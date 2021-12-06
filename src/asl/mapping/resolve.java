// Internal action code for project ia_submission

package mapping;

import java.util.List;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class resolve extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'mapping.resolve'");
        

        int Xt = (int)((NumberTerm)args[0]).solve();
        int Yt = (int)((NumberTerm)args[1]).solve();
        int Xl = (int)((NumberTerm)args[2]).solve();
        int Yl = (int)((NumberTerm)args[3]).solve();
        
        List<Pair> stepsList = Map.resolve(Xt, Yt, Xl, Yl);
        
        ListTerm stepsX = new ListTermImpl();
        ListTerm stepsY = new ListTermImpl();
        
        for(Pair loc : stepsList) {
        	stepsX.add(new NumberTermImpl(loc.getX()));
        	stepsY.add(new NumberTermImpl(loc.getY()));
        }

        return un.unifies(stepsX, args[4]) && un.unifies(stepsY, args[5]);
    }
}
