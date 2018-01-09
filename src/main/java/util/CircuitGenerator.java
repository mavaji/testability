package util;

import circuit.Circuit;
import circuit.logicelements.Wire;
import circuit.logicelements.logicgates.*;
import util.parser.Keywords;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

/**
 * This class generates the whole levels from a netlist.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class CircuitGenerator implements Symbols, Keywords {
    /**
     * Input data which the levels is constructed on the basis of.
     */
    private Vector logicGateItems;
    /**
     * The axiom of the levels that it's <code>i</code>th element holds logicGates of level <code>i</code>.
     */
    private Vector levels = new Vector();
    /**
     * List of logicGates that is sorted according to level of logicGates and is updated while reading input data.
     */
    private Vector logicGates = new Vector();
    /**
     * The current logic gate that is constructed and it's level is to be computed.
     */
    private AbstractLogicGate logicGate = null;

    /**
     * The Constructor method.
     *
     * @param logicGateItems
     */
    public CircuitGenerator(Vector logicGateItems) {
        this.logicGateItems = logicGateItems;
    }

    /**
     * Generates the whole circiut with real gates.
     * The corressponding wires will be connected together.
     *
     * @return The structure of the levels as a list of levelized gates.
     *         Each element of the list is itself another list of the gates with the same level.
     */
    public Circuit generate() {
        LogicGateItem logicGateItem = null;

        // reading the first input information.
        Iterator logicGateItemsIter = logicGateItems.iterator();
        if (logicGateItemsIter.hasNext()) {
            logicGateItem = (LogicGateItem) logicGateItemsIter.next();
            logicGate = createLogicGate(logicGateItem);
            logicGates.add(logicGate);
        }

        // reading the input information.
        while (logicGateItemsIter.hasNext()) {
            logicGateItem = (LogicGateItem) logicGateItemsIter.next();
            logicGate = createLogicGate(logicGateItem);
            AbstractLogicGate gate;

            // attempting to find the level of the new logic gate.
            for (Iterator logicGatesIter = logicGates.iterator(); logicGatesIter.hasNext(); ) {
                gate = (AbstractLogicGate) logicGatesIter.next();

                // connects the new gate to appropriate gates.
                for (Iterator gateInputs = gate.getInputs().iterator(); gateInputs.hasNext(); ) {
                    Wire gateInput = (Wire) gateInputs.next();
                    if (gateInput.getName().equals(logicGate.getOutput().getName())) {
                        logicGate.getOutput().connect(gateInput);
                    }
                }
                for (Iterator logicGateInputs = logicGate.getInputs().iterator(); logicGateInputs.hasNext(); ) {
                    Wire logicGateInput = (Wire) logicGateInputs.next();
                    if (logicGateInput.getName().equals(gate.getOutput().getName())) {
                        gate.getOutput().connect(logicGateInput);
                    }
                }

                // updates the level value of the connected gates.
                if (logicGate.getInputs().contains(gate.getOutput())) {
                    if (logicGate.level < gate.level + 1) {
                        logicGate.level = gate.level + 1;
                    }
                    updateConnectedGates(logicGate);
                } else if (gate.getInputs().contains(logicGate.getOutput())) {
                    updateConnectedGates(logicGate);
                }
            }

            logicGates.add(logicGate);
            Collections.sort(logicGates);
        }

        // constructing the levelized list.
        AbstractLogicGate gate;
        for (Iterator logicGatesIter = logicGates.iterator(); logicGatesIter.hasNext(); ) {
            gate = (AbstractLogicGate) logicGatesIter.next();

            if (gate.level < levels.size()) {
                ((Vector) levels.elementAt(gate.level)).add(gate);
            } else {
                Vector v = new Vector();
                v.add(gate);
                levels.add(v);
            }
        }

        return new Circuit(levels);
    }

    /**
     * Updates the level values of logicGates that are connected to the <code>logicGate</code>
     *
     * @param logicGate The gate that it's connected logicGates's levels must be updated.
     */
    private void updateConnectedGates(AbstractLogicGate logicGate) {
        AbstractLogicGate gate;

        for (Iterator gatesIter = logicGates.iterator(); gatesIter.hasNext(); ) {
            gate = (AbstractLogicGate) gatesIter.next();

            if (gate.getInputs().contains(logicGate.getOutput())) {
                if (gate.level < logicGate.level + 1) {
                    gate.level = logicGate.level + 1;
                }
                updateConnectedGates(gate);
            }
        }
    }

    /**
     * Creates a real gate according to a logic gate item information.
     *
     * @param logicGateItem Information about the gate
     * @return The constructed logic gate
     */
    private AbstractLogicGate createLogicGate(LogicGateItem logicGateItem) {
        AbstractLogicGate result = null;

        if (logicGateItem.getType().equals(__and)) {
            result = new AND(logicGateItem);
        } else if (logicGateItem.getType().equals(__buf)) {
            result = new BUF(logicGateItem);
        } else if (logicGateItem.getType().equals(__bufif0)) {
            result = new BUFIF0(logicGateItem);
        } else if (logicGateItem.getType().equals(__bufif1)) {
            result = new BUFIF1(logicGateItem);
        } else if (logicGateItem.getType().equals(__nand)) {
            result = new NAND(logicGateItem);
        } else if (logicGateItem.getType().equals(__nor)) {
            result = new NOR(logicGateItem);
        } else if (logicGateItem.getType().equals(__not)) {
            result = new NOT(logicGateItem);
        } else if (logicGateItem.getType().equals(__notif0)) {
            result = new NOTIF0(logicGateItem);
        } else if (logicGateItem.getType().equals(__notif1)) {
            result = new NOTIF1(logicGateItem);
        } else if (logicGateItem.getType().equals(__or)) {
            result = new OR(logicGateItem);
        } else if (logicGateItem.getType().equals(__xnor)) {
            result = new XNOR(logicGateItem);
        } else if (logicGateItem.getType().equals(__xor)) {
            result = new XOR(logicGateItem);
        }

        return result;
    }
}
