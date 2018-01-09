package circuit.delay;

import util.Symbols;
import util.parser.Keywords;

/**
 * This class represents delay of a logic gate that is composed of three delay elements:
 * rise, fall, turnoff delays which each delay is a combination of min, typ, max values.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class GateDelay implements Symbols, Keywords {
    /**
     * Rise delay component of this gate delay.
     */
    private Delay rise = new Delay();
    /**
     * Fall delay component of this gate delay.
     */
    private Delay fall = null;
    /**
     * Turnoff delay component of this gate delay.
     */
    private Delay turnoff = null;
    /**
     * If <code>true</code> the delay is transport else inertial.
     */
    private boolean isTransport = false;

    public GateDelay() {
    }

    public GateDelay(Delay rise, Delay fall, Delay turnOff) {
        this.rise = rise;
        this.fall = fall;
        this.turnoff = turnOff;
    }

    public GateDelay setRise(Delay rise) {
        this.rise = rise;
        return this;
    }

    public GateDelay setFall(Delay fall) {
        this.fall = fall;
        return this;
    }

    public GateDelay setTurnOff(Delay turnOff) {
        this.turnoff = turnOff;
        return this;
    }

    public void setTransport(boolean transport) {
        isTransport = transport;
    }

    public Delay getRise() {
        return rise;
    }

    public Delay getFall() {
        if (fall == null) {
            return rise;
        } else {
            return fall;
        }
    }

    public Delay getTurnoff() {
        if (turnoff == null) {
            return (getRise().getDelay() > getFall().getDelay() ? getRise() : getFall());
        } else {
            return turnoff;
        }
    }

    public boolean isTransport() {
        return isTransport;
    }

    public int getDelay() {
        int delay = 0;
        int count = 0;

        int riseDelay = getRise().getDelay();
        int fallDelay = getFall().getDelay();
        int turnoffDelay = getTurnoff().getDelay();

        if (riseDelay != 0) {
            count++;
            delay += riseDelay;
        }
        if (fallDelay != 0) {
            count++;
            delay += fallDelay;
        }
        if (turnoffDelay != 0) {
            count++;
            delay += turnoffDelay;
        }

        if (count != 0) {
            delay /= count;
        }
        return delay;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append($LESS + $DELAY + $BLANK);

        stringBuffer.append($TYPE + $EQUAL + $DOUBLE_QUOT
                + (isTransport ? $TRANSPORT : $INERTIAL)
                + $DOUBLE_QUOT + $BLANK);

        stringBuffer.append($AVERAGE + $EQUAL + $DOUBLE_QUOT + getDelay() + $DOUBLE_QUOT + $GREATER + $NEW_LINE);

        stringBuffer.append($TAB + $LESS + $RISE + $BLANK + getRise().toString() + $SLASH + $GREATER + $NEW_LINE);
        stringBuffer.append($TAB + $LESS + $FALL + $BLANK + getFall().toString() + $SLASH + $GREATER + $NEW_LINE);
        stringBuffer.append($TAB + $LESS + $TURN_OFF + $BLANK + getTurnoff().toString() + $SLASH + $GREATER + $NEW_LINE);

        stringBuffer.append($LESS + $SLASH + $DELAY + $GREATER);
        return stringBuffer.toString();
    }
}
