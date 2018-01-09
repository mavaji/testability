package circuit.logicelements.logicgates;

import circuit.logicelements.Wire;
import util.LogicGateItem;
import util.Vectors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * The <code>BUFFER</code> logic gate.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class BUF extends AbstractLogicGate {
    public BUF(LogicGateItem logicGateItem) {
        super(logicGateItem);
        type = __buf;
    }

    public String logicFunction() {
        Iterator iter = inputs.iterator();
        String result = $U;

        if (iter.hasNext()) {
            result = ((Wire) iter.next()).getValue();
        }

        return result;
    }

    public Vector collapse(Vector collapsedFaults) {
//        Fault outFault0 = new Fault(output, $0);
//        Fault outFault1 = new Fault(output, $1);
//
//        collapsedFaults.remove(outFault0);
//        collapsedFaults.remove(outFault1);

        // It is used to simulate a branch point
        // because Verilog syntax doesnt provide any means for implementing branchs.

        return collapsedFaults;
    }

    public Vector getOutputFaultList(HashMap recent) {
        Vector result = new Vector();
        result = Vectors.union(result, (Vector) recent.get(((Wire) inputs.firstElement()).getName()));
        return result;
    }
}
