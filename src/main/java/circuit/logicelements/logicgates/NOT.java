package circuit.logicelements.logicgates;

import circuit.Fault;
import circuit.logicelements.Wire;
import util.LogicGateItem;
import util.Vectors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * The <code>NOT</code> logic gate.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class NOT extends AbstractLogicGate {
    public NOT(LogicGateItem logicGateItem) {
        super(logicGateItem);
        type = __not;
    }

    public String logicFunction() {
        Iterator iter = inputs.iterator();
        String result = $U;

        if (iter.hasNext()) {
            result = ((Wire) iter.next()).getValue();
        }

        if (result.equals($0)) {
            result = $1;
        } else if (result.equals($1)) {
            result = $0;
        } else if (result.equals($U)) {
            result = $U;
        } else if (result.equals($D)) {
            result = $D_BAR;
        } else if (result.equals($D_BAR)) {
            result = $D;
        } else if (result.equals($G0)) {
            result = $G1;
        } else if (result.equals($G1)) {
            result = $G0;
        } else if (result.equals($F0)) {
            result = $F1;
        } else if (result.equals($F1)) {
            result = $F0;
        }

        return result;
    }

    public Vector collapse(Vector collapsedFaults) {
        Fault outFault0 = new Fault(output, $0);
        Fault outFault1 = new Fault(output, $1);

        collapsedFaults.remove(outFault0);
        collapsedFaults.remove(outFault1);

        return collapsedFaults;
    }

    public Vector getOutputFaultList(HashMap recent) {
        Vector result = new Vector();
        result = Vectors.union(result, (Vector) recent.get(((Wire) inputs.firstElement()).getName()));
        return result;
    }
}
