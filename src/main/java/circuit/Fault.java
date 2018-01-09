package circuit;

import circuit.logicelements.Wire;
import util.Symbols;

/**
 * This class represents a stuck-at fault.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class Fault implements Symbols {
    private String wireName;
    private String faultValue;

    public Fault(Object wire, String faultValue) {
        this.wireName = ((Wire) wire).getName();
        this.faultValue = faultValue;
    }

    public Fault(Wire wire, String faultValue) {
        this.wireName = wire.getName();
        this.faultValue = faultValue;
    }

    public Fault(String wireName, String faultValue) {
        this.wireName = wireName;
        this.faultValue = faultValue;
    }

    public String getWireName() {
        return wireName;
    }

    public String getFaultValue() {
        return faultValue;
    }

    public String toString() {
        return wireName + $AT_SIGN + faultValue;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Fault)) {
            return false;
        }

        Fault other = (Fault) obj;
        if (this.wireName.equals(other.wireName) && this.faultValue.equals(other.faultValue)) {
            return true;
        } else {
            return false;
        }
    }
}
