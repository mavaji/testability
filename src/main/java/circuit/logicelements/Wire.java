package circuit.logicelements;

import util.Symbols;
import util.parser.Keywords;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * This class represent a wire. It can change it's corresponding attached wires's values or
 * It's value can be changed when it's driving wires's values do change.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class Wire extends Observable implements Observer, Symbols, Keywords {
    /**
     * Value of this wire, initially unknown.
     */
    private String value = $U;
    /**
     * If <code>true</code> the wire is stuck at some logic value.
     */
    private boolean isStuckAt = false;
    /**
     * Name of this wire.
     */
    private String name;

    public Wire(Wire wire) {
        this.name = wire.name;
    }

    public Wire(String name) {
        this.name = name;
    }

    public void setStuckAt(String value) {
        this.value = value;
        isStuckAt = true;
        setChanged();
        notifyObservers();
    }

    public void resetValue(String value) {
        this.value = value;
        isStuckAt = false;
        setChanged();
        notifyObservers();
    }

    public void connect(Wire anotherWire) {
        addObserver(anotherWire);
    }

    public void connect(Vector anotherWires) {
        for (Iterator i = anotherWires.iterator(); i.hasNext(); ) {
            addObserver((Wire) i.next());
        }
    }

    public String getValue() {
        return value;
    }

    public boolean isStuckAt() {
        return isStuckAt;
    }

    public String getName() {
        return name;
    }

    public void update(Observable wire, Object value) {
        if (!(wire instanceof Wire) || !(value instanceof String)) {
            return;
        }
        if (!isStuckAt) {
            this.value = (String) value;
            setChanged();
            notifyObservers();
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Wire)) {
            return false;
        }
        if (this.name.equals(((Wire) obj).getName())) {
            return true;
        } else {
            return false;
        }
    }
}
