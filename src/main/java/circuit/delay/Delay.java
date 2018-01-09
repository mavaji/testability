package circuit.delay;

import util.Symbols;
import util.parser.Keywords;

/**
 * This class represents a delay that is composed of three values:
 * minimum, typical, maximum delay valuse.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class Delay implements Symbols, Keywords {
    /**
     * Minimum value of the delay.
     */
    private int minValue = 0;
    /**
     * Typical value of the delay.
     */
    private int typValue = 0;
    /**
     * Maximum value of the delay.
     */
    private int maxValue = 0;

    public Delay() {
    }

    public Delay(int minValue, int maxValue, int typValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.typValue = typValue;
    }

    public Delay setMinValue(int minValue) {
        this.minValue = minValue;
        return this;
    }

    public Delay setTypValue(int typValue) {
        this.typValue = typValue;
        return this;
    }

    public Delay setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getTypValue() {
        return typValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getDelay() {
        int delay = 0;
        int count = 0;

        if (minValue != 0) {
            count++;
            delay += minValue;
        }
        if (typValue != 0) {
            count++;
            delay += typValue;
        }
        if (maxValue != 0) {
            count++;
            delay += maxValue;
        }

        if (count != 0) {
            delay /= count;
        }
        return delay;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append($AVERAGE + $EQUAL + $DOUBLE_QUOT + getDelay() + $DOUBLE_QUOT);
        if (minValue != 0) {
            stringBuffer.append($BLANK + $MIN + $EQUAL + $DOUBLE_QUOT + minValue + $DOUBLE_QUOT);
        }

        if (typValue != 0) {
            stringBuffer.append($BLANK + $TYP + $EQUAL + $DOUBLE_QUOT + typValue + $DOUBLE_QUOT);
        }

        if (maxValue != 0) {
            stringBuffer.append($BLANK + $MAX + $EQUAL + $DOUBLE_QUOT + maxValue + $DOUBLE_QUOT);
        }

        return stringBuffer.toString();
    }
}
