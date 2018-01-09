package circuit;

import circuit.logicelements.Wire;
import util.Symbols;

import java.util.Iterator;
import java.util.Vector;

/**
 * This class represents an event on a wire.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class Event implements Comparable, Symbols {
    /**
     * Wire which it's value is to be changed when the event occurres.
     */
    private Wire wire;
    /**
     * Time of occurring the event.
     */
    private int time;
    /**
     * New value of the wire when the event occurres.
     */
    private String value;
    /**
     * List of faults that with deductive method appears on the wire.
     */
    private Vector faults = new Vector();

    public Event(Wire wire, int time, String value) {
        this.wire = new Wire(wire);
        this.time = time;
        this.value = value;
    }

    public Wire getWire() {
        return wire;
    }

    public int getTime() {
        return time;
    }

    public String getValue() {
        return value;
    }

    public Vector getFaults() {
        return faults;
    }

    public void setFaults(Vector faults) {
        this.faults = faults;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append($LESS + $EVENT + $BLANK);

        stringBuffer.append($WIRE + $EQUAL + $DOUBLE_QUOT + wire.getName() + $DOUBLE_QUOT + $BLANK);
        stringBuffer.append($TIME + $EQUAL + $DOUBLE_QUOT + time + $DOUBLE_QUOT + $BLANK);
        stringBuffer.append($VALUE + $EQUAL + $DOUBLE_QUOT + value + $DOUBLE_QUOT + $GREATER + $NEW_LINE);

        if (!faults.isEmpty()) {
            stringBuffer.append($TAB + $LESS + $DEDUCTION + $GREATER + $NEW_LINE);

            stringBuffer.append($TAB + $TAB + $OPEN_BRACKET);
            for (Iterator iter = faults.iterator(); iter.hasNext(); ) {
                stringBuffer.append(iter.next() + $COMMA + $BLANK);
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            stringBuffer.append($CLOSE_BRACKET + $NEW_LINE);
            stringBuffer.append($TAB + $LESS + $SLASH + $DEDUCTION + $GREATER + $NEW_LINE);
        }

        stringBuffer.append($LESS + $SLASH + $EVENT + $GREATER);

        return stringBuffer.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Event)) {
            return false;
        }
        Event other = (Event) obj;
        if (this.wire.equals(other.wire) && this.time == other.time) {
            return true;
        } else {
            return false;
        }
    }

    public int compareTo(Object o) {
        int result = 0;
        Event other = (Event) o;
        if (this.time < other.time) {
            return -1;
        } else if (this.time == other.time) {
            return 0;
        } else if (this.time > other.time) {
            return 1;
        }

        return result;
    }
}
