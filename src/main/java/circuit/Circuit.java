package circuit;

import circuit.logicelements.Wire;
import circuit.logicelements.logicgates.AbstractLogicGate;
import util.Symbols;
import util.Vectors;
import util.parser.Keywords;

import java.util.Iterator;
import java.util.Vector;

/**
 * This class represents a whole circuit with it's levelized gates with it's collapsed faults.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class Circuit implements Symbols, Keywords {
    /**
     * A list that ith element of it is itself list of logic elements at level i.
     */
    private Vector levels;
    /**
     * Number of primary inputs of this circuit.
     */
    private int numberOfPrimaryInputs;
    /**
     * Number of primary outputs of this circuit.
     */
    private int numberOfPrimaryOutputs;
    /**
     * List of primary inputs.
     */
    private Vector primaryInputs = new Vector();
    /**
     * List of primary outputs.
     */
    private Vector primaryOutputs = new Vector();
    /**
     * List of all faults that this circuis has.
     */
    private Vector allFaults = new Vector();
    /**
     * List of collapsed faults of this circuit.
     */
    private Vector collapsedFaults = new Vector();

    public Circuit(Vector levels) {
        this.levels = levels;
        init();
    }

    private void init() {
        AbstractLogicGate logicGate;
        Vector outputs = new Vector();

        for (Iterator levelsIter = levels.iterator(); levelsIter.hasNext(); ) {
            Vector level = (Vector) levelsIter.next();
            for (Iterator levelIter = level.iterator(); levelIter.hasNext(); ) {
                logicGate = (AbstractLogicGate) levelIter.next();
                outputs.add(logicGate.getOutput());

                for (Iterator inputsIter = logicGate.getInputs().iterator(); inputsIter.hasNext(); ) {
                    Wire input = (Wire) inputsIter.next();
                    if (!outputs.contains(input) && !primaryInputs.contains(input)) {
                        primaryInputs.add(input);
                    }
                }
            }
        }
        numberOfPrimaryInputs = primaryInputs.size();

        Vector inputs = new Vector();
        for (int i = levels.size() - 1; i >= 0; i--) {
            Vector level = (Vector) levels.elementAt(i);
            for (Iterator levelIter = level.iterator(); levelIter.hasNext(); ) {
                logicGate = (AbstractLogicGate) levelIter.next();
                inputs = Vectors.union(inputs, logicGate.getInputs());
                Wire output = logicGate.getOutput();
                if (!inputs.contains(output) && !primaryOutputs.contains(output)) {
                    primaryOutputs.add(output);
                }
            }
        }
        numberOfPrimaryOutputs = primaryOutputs.size();

        makeAllFaults();
        makeCollapsedFaults();
    }

    private void makeAllFaults() {
        Vector level = new Vector();
        AbstractLogicGate logicGate;

        for (Iterator levelsIter = levels.iterator(); levelsIter.hasNext(); ) {
            level = (Vector) levelsIter.next();

            for (Iterator levelIter = level.iterator(); levelIter.hasNext(); ) {
                logicGate = (AbstractLogicGate) levelIter.next();
                allFaults = Vectors.union(allFaults, logicGate.getFaults());
            }
        }
    }

    private void makeCollapsedFaults() {
        collapsedFaults = new Vector(allFaults);
        Vector level;
        AbstractLogicGate logicGate;

        for (Iterator levelsIter = levels.iterator(); levelsIter.hasNext(); ) {
            level = (Vector) levelsIter.next();

            for (Iterator levelIter = level.iterator(); levelIter.hasNext(); ) {
                logicGate = (AbstractLogicGate) levelIter.next();
                collapsedFaults = logicGate.collapse(collapsedFaults);
            }
        }
    }

    public void reset() {
        Vector level;
        AbstractLogicGate logicGate;

        for (Iterator levelsIter = levels.iterator(); levelsIter.hasNext(); ) {
            level = (Vector) levelsIter.next();

            for (Iterator levelIter = level.iterator(); levelIter.hasNext(); ) {
                logicGate = (AbstractLogicGate) levelIter.next();
                logicGate.resetValues();
            }
        }
    }

    public Vector getLevels() {
        return levels;
    }

    public int getNumberOfPrimaryInputs() {
        return numberOfPrimaryInputs;
    }

    public int getNumberOfPrimaryOutputs() {
        return numberOfPrimaryOutputs;
    }

    public Vector getPrimaryInputs() {
        return primaryInputs;
    }

    public Vector getPrimaryOutputs() {
        return primaryOutputs;
    }

    public Vector getAllFaults() {
        return allFaults;
    }

    public Vector getCollapsedFaults() {
        return collapsedFaults;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append($LESS + $CIRCUIT + $GREATER + $NEW_LINE);

        for (int i = 0; i < levels.size(); i++) {
            stringBuffer.append($TAB + $LESS + $LEVEL + $UNDER_LINE + i + $GREATER + $NEW_LINE);
            Vector level = (Vector) levels.elementAt(i);
            String logicGateStr;
            for (Iterator iter = level.iterator(); iter.hasNext(); ) {
                logicGateStr = ((AbstractLogicGate) iter.next()).toString();
                logicGateStr = logicGateStr.replaceAll($NEW_LINE, $NEW_LINE + $TAB + $TAB);
                logicGateStr = $TAB + $TAB + logicGateStr;
                stringBuffer.append(logicGateStr + $NEW_LINE);
            }
            stringBuffer.append($TAB + $LESS + $SLASH + $LEVEL + $UNDER_LINE + i + $GREATER + $NEW_LINE);
        }

        stringBuffer.append($TAB + $LESS + $ALL_FAULTS + $BLANK);
        stringBuffer.append($SIZE + $EQUAL + $DOUBLE_QUOT + allFaults.size() + $DOUBLE_QUOT + $GREATER + $NEW_LINE);
        stringBuffer.append($TAB + $TAB + $OPEN_BRACKET);
        for (Iterator iter = allFaults.iterator(); iter.hasNext(); ) {
            stringBuffer.append(iter.next() + $COMMA + $BLANK);
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append($CLOSE_BRACKET + $NEW_LINE);
        stringBuffer.append($TAB + $LESS + $SLASH + $ALL_FAULTS + $GREATER + $NEW_LINE);

        stringBuffer.append($TAB + $LESS + $COLLAPSED_FAULTS + $BLANK);
        stringBuffer.append($SIZE + $EQUAL + $DOUBLE_QUOT + collapsedFaults.size()
                + $DOUBLE_QUOT + $GREATER + $NEW_LINE);
        stringBuffer.append($TAB + $TAB + $OPEN_BRACKET);
        for (Iterator iter = collapsedFaults.iterator(); iter.hasNext(); ) {
            stringBuffer.append(iter.next() + $COMMA + $BLANK);
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append($CLOSE_BRACKET + $NEW_LINE);
        stringBuffer.append($TAB + $LESS + $SLASH + $COLLAPSED_FAULTS + $GREATER + $NEW_LINE);

        stringBuffer.append($LESS + $SLASH + $CIRCUIT + $GREATER);
        return stringBuffer.toString();
    }
}
