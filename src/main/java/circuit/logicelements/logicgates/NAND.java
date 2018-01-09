package circuit.logicelements.logicgates;

import circuit.Fault;
import circuit.logicelements.Wire;
import util.LogicGateItem;
import util.Vectors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * The <code>NAND</code> logic gate.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class NAND extends AbstractLogicGate {
    public NAND(LogicGateItem logicGateItem) {
        super(logicGateItem);
        type = __nand;
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
                result = $0;
            } else if (value.equals($1)) {
                // result = result;
            } else if (value.equals($U)) {
                if (result.equals($0)) {
                    result = $0;
                } else if (result.equals($1)) {
                    result = $U;
                } else if (result.equals($U)) {
                    result = $U;
                } else if (result.equals($D)) {
                    result = $F0;
                } else if (result.equals($D_BAR)) {
                    result = $G0;
                } else if (result.equals($G0)) {
                    result = $G0;
                } else if (result.equals($G1)) {
                    result = $U;
                } else if (result.equals($F0)) {
                    result = $F0;
                } else if (result.equals($F1)) {
                    result = $U;
                }

            } else if (value.equals($D)) {
                if (result.equals($0)) {
                    result = $0;
                } else if (result.equals($1)) {
                    result = $D;
                } else if (result.equals($U)) {
                    result = $F0;
                } else if (result.equals($D)) {
                    result = $D;
                } else if (result.equals($D_BAR)) {
                    result = $0;
                } else if (result.equals($G0)) {
                    result = $0;
                } else if (result.equals($G1)) {
                    result = $D;
                } else if (result.equals($F0)) {
                    result = $F0;
                } else if (result.equals($F1)) {
                    result = $F0;
                }

            } else if (value.equals($D_BAR)) {
                if (result.equals($0)) {
                    result = $0;
                } else if (result.equals($1)) {
                    result = $D_BAR;
                } else if (result.equals($U)) {
                    result = $G0;
                } else if (result.equals($D)) {
                    result = $0;
                } else if (result.equals($D_BAR)) {
                    result = $D_BAR;
                } else if (result.equals($G0)) {
                    result = $G0;
                } else if (result.equals($G1)) {
                    result = $G0;
                } else if (result.equals($F0)) {
                    result = $0;
                } else if (result.equals($F1)) {
                    result = $D_BAR;
                }

            } else if (value.equals($G0)) {
                if (result.equals($0)) {
                    result = $0;
                } else if (result.equals($1)) {
                    result = $G0;
                } else if (result.equals($U)) {
                    result = $G0;
                } else if (result.equals($D)) {
                    result = $0;
                } else if (result.equals($D_BAR)) {
                    result = $G0;
                } else if (result.equals($G0)) {
                    result = $G0;
                } else if (result.equals($G1)) {
                    result = $G0;
                } else if (result.equals($F0)) {
                    result = $0;
                } else if (result.equals($F1)) {
                    result = $G0;
                }

            } else if (value.equals($G1)) {
                if (result.equals($0)) {
                    result = $0;
                } else if (result.equals($1)) {
                    result = $G1;
                } else if (result.equals($U)) {
                    result = $U;
                } else if (result.equals($D)) {
                    result = $D;
                } else if (result.equals($D_BAR)) {
                    result = $G0;
                } else if (result.equals($G0)) {
                    result = $G0;
                } else if (result.equals($G1)) {
                    result = $G1;
                } else if (result.equals($F0)) {
                    result = $F0;
                } else if (result.equals($F1)) {
                    result = $U;
                }

            } else if (value.equals($F0)) {
                if (result.equals($0)) {
                    result = $0;
                } else if (result.equals($1)) {
                    result = $F0;
                } else if (result.equals($U)) {
                    result = $F0;
                } else if (result.equals($D)) {
                    result = $F0;
                } else if (result.equals($D_BAR)) {
                    result = $0;
                } else if (result.equals($G0)) {
                    result = $0;
                } else if (result.equals($G1)) {
                    result = $F0;
                } else if (result.equals($F0)) {
                    result = $F0;
                } else if (result.equals($F1)) {
                    result = $F0;
                }

            } else if (value.equals($F1)) {
                if (result.equals($0)) {
                    result = $0;
                } else if (result.equals($1)) {
                    result = $F1;
                } else if (result.equals($U)) {
                    result = $U;
                } else if (result.equals($D)) {
                    result = $F0;
                } else if (result.equals($D_BAR)) {
                    result = $D_BAR;
                } else if (result.equals($G0)) {
                    result = $G0;
                } else if (result.equals($G1)) {
                    result = $U;
                } else if (result.equals($F0)) {
                    result = $F0;
                } else if (result.equals($F1)) {
                    result = $F1;
                }

            }
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
        Object input;
        Fault inFault0;

        collapsedFaults.remove(outFault0);
        collapsedFaults.remove(outFault1);

        boolean flag = false;
        for (Iterator inputsIter = inputs.iterator(); inputsIter.hasNext(); ) {
            input = inputsIter.next();
            inFault0 = new Fault(input, $0);
            if (!collapsedFaults.contains(inFault0)) {
                flag = true;
            } else {
                collapsedFaults.remove(inFault0);
            }
        }
        if (flag == false) {
            collapsedFaults.add(new Fault(inputs.firstElement(), $0));
        }

        return collapsedFaults;
    }

    public Vector getOutputFaultList(HashMap recent) {
        Vector result = new Vector();
        if (logicFunction().equals($0)) {
            for (Iterator inputsIter = inputs.iterator(); inputsIter.hasNext(); ) {
                result = Vectors.union(result, (Vector) recent.get(((Wire) inputsIter.next()).getName()));
            }
        } else if (logicFunction().equals($1)) {
            Vector zeros = new Vector();
            Vector ones = new Vector();
            Wire input;
            for (Iterator inputsIter = inputs.iterator(); inputsIter.hasNext(); ) {
                input = (Wire) inputsIter.next();
                if (input.getValue().equals($0)) {
                    if (zeros.isEmpty()) {
                        zeros = new Vector((Vector) recent.get(input.getName()));
                    } else {
                        zeros = Vectors.common(zeros, (Vector) recent.get(input.getName()));
                    }
                } else if (input.getValue().equals($1)) {
                    if (ones.isEmpty()) {
                        ones = new Vector((Vector) recent.get(input.getName()));
                    } else {
                        ones = Vectors.union(ones, (Vector) recent.get(input.getName()));
                    }
                }
            }

            result = Vectors.minus(zeros, ones);
        }

        return result;
    }
}
