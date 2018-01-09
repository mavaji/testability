package util;

import circuit.delay.GateDelay;
import util.parser.Keywords;

import java.util.Vector;

/**
 * This class encapsulates a logic gate including it's
 * type, delay, name, output and inputs.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class LogicGateItem implements Symbols, Keywords {
    /**
     * Type of this logic gate.
     */
    private String type = $ANONYMOUS;
    /**
     * Delay values associated with this logic gate.
     */
    private GateDelay delay = new GateDelay();
    /**
     * Name of this logic gate.
     */
    private String name = $ANONYMOUS;
    /**
     * Output name of this logic gate.
     */
    private String output = $ANONYMOUS;
    /**
     * Inputs names of this logic gate.
     */
    private Vector inputs = new Vector();

    public LogicGateItem() {
    }

    public LogicGateItem(String type, String name, Vector inputs, String output, GateDelay delay) {
        this.type = type;
        this.name = name;
        this.inputs = inputs;
        this.output = output;
        this.delay = delay;
    }

    public LogicGateItem(String type, Vector inputs, String output) {
        this.type = type;
        this.inputs = inputs;
        this.output = output;
    }

    public LogicGateItem setType(String type) {
        this.type = type;
        return this;
    }

    public LogicGateItem setName(String name) {
        this.name = name;
        return this;
    }

    public LogicGateItem setInputs(Vector inputs) {
        this.inputs = inputs;
        return this;
    }

    public LogicGateItem setOutput(String output) {
        this.output = output;
        return this;
    }

    public LogicGateItem setDelay(GateDelay delay) {
        this.delay = delay;
        return this;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Vector getInputs() {
        return inputs;
    }

    public String getOutput() {
        return output;
    }

    public GateDelay getDelay() {
        return delay;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append($LOGIC_GATE + $BLANK + $COLON + $BLANK + $NEW_LINE);
        stringBuffer.append($TYPE + $BLANK + $COLON + $BLANK + type + $NEW_LINE);
        stringBuffer.append($DELAY + $BLANK + $COLON + $BLANK + delay + $NEW_LINE);
        stringBuffer.append($NAME + $BLANK + $COLON + $BLANK + name + $NEW_LINE);
        stringBuffer.append($OUTPUT + $BLANK + $COLON + $BLANK + output + $NEW_LINE);
        stringBuffer.append($INPUTS + $BLANK + $COLON + $BLANK + $OPEN_PARENTHESIS);
        stringBuffer.append((String) inputs.firstElement());
        for (int i = 1; i < inputs.size(); i++) {
            stringBuffer.append($COMMA + $BLANK + (String) inputs.elementAt(i));
        }
        stringBuffer.append($CLOSE_PARENTHESIS + $NEW_LINE);

        return stringBuffer.toString();
    }
}
