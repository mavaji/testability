package circuit.logicelements.logicgates;

import circuit.Fault;
import circuit.delay.GateDelay;
import circuit.logicelements.Wire;
import util.LogicGateItem;
import util.Symbols;
import util.parser.Keywords;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * The base class of all logic gates.
 * Every <code>LogicGateItem</code> must be mapped into a real logic gate.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public abstract class AbstractLogicGate implements Comparable, Symbols, Keywords {
    /**
     * Level of this logic gate in the circuit.
     */
    public int level = 0;

    /**
     * Output of this logic gate.
     */
    protected Wire output;
    /**
     * Inputs of this logic gate. Each input is a wire itself.
     */
    protected Vector inputs;
    /**
     * Delay values of this logic gate.
     */
    protected GateDelay delay;
    /**
     * Name of this logic gate.
     */
    protected String name;
    /**
     * Type of this logic gate.
     */
    protected String type;
    /**
     * List of faults on inputs and output of this logic gate.
     */
    protected Vector faults;

    protected AbstractLogicGate(LogicGateItem logicGateItem) {
        Wire out = new Wire(logicGateItem.getOutput());
        this.output = out;

        this.inputs = new Vector();
        Wire in;
        for (int i = 0; i < logicGateItem.getInputs().size(); i++) {
            in = new Wire((String) logicGateItem.getInputs().elementAt(i));
            this.inputs.add(in);
        }

        this.delay = logicGateItem.getDelay();

        this.name = logicGateItem.getName();

        faults = new Vector();
        faults.add(new Fault(output, $0));
        faults.add(new Fault(output, $1));
        Object input;
        for (Iterator inputsIter = inputs.iterator(); inputsIter.hasNext(); ) {
            input = inputsIter.next();
            faults.add(new Fault(input, $0));
            faults.add(new Fault(input, $1));
        }
    }

    public abstract String logicFunction();

    public abstract Vector collapse(Vector collapsedFaults);

    public abstract Vector getOutputFaultList(HashMap recent);

    public void resetValues() {
        output.resetValue($U);
        for (Iterator inputsIter = inputs.iterator(); inputsIter.hasNext(); ) {
            ((Wire) inputsIter.next()).resetValue($U);
        }
    }

    public Vector getFaults() {
        return faults;
    }

    public Wire getOutput() {
        return output;
    }

    public Vector getInputs() {
        return inputs;
    }

    public GateDelay getDelay() {
        return delay;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append($LESS + $LOGIC_GATE + $BLANK);
        stringBuffer.append($TYPE + $EQUAL + $DOUBLE_QUOT + type + $DOUBLE_QUOT + $BLANK);
        stringBuffer.append($NAME + $EQUAL + $DOUBLE_QUOT + name + $DOUBLE_QUOT + $BLANK);
        stringBuffer.append($OUTPUT + $EQUAL + $DOUBLE_QUOT + output.getName() + $DOUBLE_QUOT + $GREATER + $NEW_LINE);

        stringBuffer.append($TAB + $LESS + $INPUTS + $GREATER + $NEW_LINE + $TAB + $TAB + $OPEN_PARENTHESIS);
        stringBuffer.append(((Wire) inputs.firstElement()).getName());
        for (int i = 1; i < inputs.size(); i++) {
            stringBuffer.append($COMMA + $BLANK + ((Wire) inputs.elementAt(i)).getName());
        }
        stringBuffer.append($CLOSE_PARENTHESIS + $NEW_LINE
                + $TAB + $LESS + $SLASH + $INPUTS + $GREATER + $NEW_LINE);

        String delayStr = delay.toString();
        delayStr = delayStr.replaceAll($NEW_LINE, $NEW_LINE + $TAB);
        delayStr = $TAB + delayStr;
        stringBuffer.append(delayStr + $NEW_LINE);

        stringBuffer.append($LESS + $SLASH + $LOGIC_GATE + $GREATER);

        return stringBuffer.toString();
    }

    public int compareTo(Object o) {
        AbstractLogicGate other = (AbstractLogicGate) o;
        int result = 0;
        if (this.level > other.level) {
            result = 1;
        } else if (this.level == other.level) {
            result = 0;
        } else if (this.level < other.level) {
            result = -1;
        }

        return result;
    }
}
