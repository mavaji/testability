package circuit.logicelements.logicgates;

import circuit.logicelements.Wire;
import util.LogicGateItem;
import util.Vectors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * The <code>XOR</code> logic gate.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class XOR extends AbstractLogicGate {
    public XOR(LogicGateItem logicGateItem) {
        super(logicGateItem);
        type = __xor;
    }

    public String logicFunction() {
        Iterator iter = inputs.iterator();
        String result = $U;
        String value;

        if (iter.hasNext()) {
            result = ((Wire) iter.next()).getValue();
        }

        while (iter.hasNext()) {
            value = ((Wire) iter.next()).getValue();
            if (value.equals($0)) {
                // result = result;
            } else if (value.equals($1)) {
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

            } else if (value.equals($U)) {
                result = $U;
            } else if (value.equals($D)) {
                if (result.equals($0)) {
                    result = $D;
                } else if (result.equals($1)) {
                    result = $D_BAR;
                } else if (result.equals($U)) {
                    result = $U;
                } else if (result.equals($D)) {
                    result = $0;
                } else if (result.equals($D_BAR)) {
                    result = $1;
                } else if (result.equals($G0)) {
                    result = $G1;
                } else if (result.equals($G1)) {
                    result = $G0;
                } else if (result.equals($F0)) {
                    result = $F0;
                } else if (result.equals($F1)) {
                    result = $F1;
                }

            } else if (value.equals($D_BAR)) {
                if (result.equals($0)) {
                    result = $D_BAR;
                } else if (result.equals($1)) {
                    result = $D;
                } else if (result.equals($U)) {
                    result = $U;
                } else if (result.equals($D)) {
                    result = $1;
                } else if (result.equals($D_BAR)) {
                    result = $0;
                } else if (result.equals($G0)) {
                    result = $G0;
                } else if (result.equals($G1)) {
                    result = $G1;
                } else if (result.equals($F0)) {
                    result = $F1;
                } else if (result.equals($F1)) {
                    result = $F0;
                }

            } else if (value.equals($G0)) {
                if (result.equals($0)) {
                    result = $G0;
                } else if (result.equals($1)) {
                    result = $G1;
                } else if (result.equals($U)) {
                    result = $U;
                } else if (result.equals($D)) {
                    result = $G1;
                } else if (result.equals($D_BAR)) {
                    result = $G0;
                } else if (result.equals($G0)) {
                    result = $G0;
                } else if (result.equals($G1)) {
                    result = $G1;
                } else if (result.equals($F0)) {
                    result = $U;
                } else if (result.equals($F1)) {
                    result = $U;
                }

            } else if (value.equals($G1)) {
                if (result.equals($0)) {
                    result = $G1;
                } else if (result.equals($1)) {
                    result = $G0;
                } else if (result.equals($U)) {
                    result = $U;
                } else if (result.equals($D)) {
                    result = $G0;
                } else if (result.equals($D_BAR)) {
                    result = $G1;
                } else if (result.equals($G0)) {
                    result = $G1;
                } else if (result.equals($G1)) {
                    result = $G0;
                } else if (result.equals($F0)) {
                    result = $U;
                } else if (result.equals($F1)) {
                    result = $U;
                }

            } else if (value.equals($F0)) {
                if (result.equals($0)) {
                    result = $F0;
                } else if (result.equals($1)) {
                    result = $F1;
                } else if (result.equals($U)) {
                    result = $U;
                } else if (result.equals($D)) {
                    result = $F0;
                } else if (result.equals($D_BAR)) {
                    result = $F1;
                } else if (result.equals($G0)) {
                    result = $U;
                } else if (result.equals($G1)) {
                    result = $U;
                } else if (result.equals($F0)) {
                    result = $F0;
                } else if (result.equals($F1)) {
                    result = $F1;
                }

            } else if (value.equals($F1)) {
                if (result.equals($0)) {
                    result = $F1;
                } else if (result.equals($1)) {
                    result = $F0;
                } else if (result.equals($U)) {
                    result = $U;
                } else if (result.equals($D)) {
                    result = $F1;
                } else if (result.equals($D_BAR)) {
                    result = $F0;
                } else if (result.equals($G0)) {
                    result = $U;
                } else if (result.equals($G1)) {
                    result = $U;
                } else if (result.equals($F0)) {
                    result = $F1;
                } else if (result.equals($F1)) {
                    result = $F0;
                }

            }
        }

        return result;
    }

    public Vector collapse(Vector collapsedFaults) {
        return collapsedFaults;
    }

    public Vector getOutputFaultList(HashMap recent) {
        //TODO this function is implemented for only 2-input gates. the general case for n-input gate must be done...
        Vector result = new Vector();
        Iterator inputsIter = inputs.iterator();

        Vector La = (Vector) recent.get(((Wire) inputsIter.next()).getName());
        Vector Lb = (Vector) recent.get(((Wire) inputsIter.next()).getName());

        result = Vectors.union(Vectors.minus(La, Lb), Vectors.minus(Lb, La));

        return result;
    }
}
