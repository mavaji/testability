package circuit.logicelements.logicgates;

import circuit.logicelements.Wire;
import util.LogicGateItem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * The <code>TriState</code> logic gate with positive logic control.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class BUFIF1 extends AbstractLogicGate {
    public BUFIF1(LogicGateItem logicGateItem) {
        super(logicGateItem);
        type = __bufif1;
    }

    public String logicFunction() {
        Iterator iter = inputs.iterator();
        String result = $U;
        String control = $U;

        if (iter.hasNext()) {
            result = ((Wire) iter.next()).getValue();
        }

        if (iter.hasNext()) {
            control = ((Wire) iter.next()).getValue();
        }

        if (control.equals($0) || control.equals($U)) {
            result = $U;
        }

        return result;
    }

    public Vector collapse(Vector collapsedFaults) {
        //TODO
        return null;
    }

    public Vector getOutputFaultList(HashMap recent) {
        //TODO
        return null;
    }
}
