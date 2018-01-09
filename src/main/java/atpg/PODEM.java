package atpg;

import circuit.Circuit;
import circuit.Event;
import circuit.Fault;
import circuit.logicelements.Wire;
import simulation.Simulator;
import util.Symbols;
import util.Vectors;
import util.parser.Keywords;

import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

/**
 * This class does all is needed for a PODEM test generation.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class PODEM implements Symbols, Keywords {
    /**
     * Circuit that PODEM algorithm is to be run on it.
     */
    private Circuit circuit;
    /**
     * A logic & fault simulator that is used to deductive fault simulation.
     */
    private Simulator simulator;
    /**
     * Number of primary inputs.
     */
    private int numberOfPrimaryInputs;
    /**
     * List of primary inputs.
     */
    private Vector primaryInputs;
    /**
     * List of primary outputs.
     */
    private Vector primaryOutputs;
    /**
     * List of initial remaining faults that the PODEM algorithm must create test vector for them.
     */
    private Vector initialFaults;
    /**
     * Report of execute algorithm.
     */
    private StringBuffer report = new StringBuffer();

    public PODEM(Circuit circuit, Vector remainingFaults) {
        this.circuit = circuit;

        numberOfPrimaryInputs = circuit.getNumberOfPrimaryInputs();
        primaryInputs = circuit.getPrimaryInputs();

        primaryOutputs = circuit.getPrimaryOutputs();

        this.initialFaults = new Vector(remainingFaults);
    }

    public void execute() {
        int initialSize = circuit.getCollapsedFaults().size();

        report.append($LESS + $PODEM + $GREATER + $NEW_LINE);

        Vector faults = new Vector(initialFaults);

        int remaining = faults.size();

        while (!faults.isEmpty()) {
            Fault fault = (Fault) faults.remove(0);
            boolean faultDetected = true;

            Stack implyStack = new Stack();

            int i = 0;
            int[] status = new int[numberOfPrimaryInputs];

            while (true) {
                if (i == -1) {
                    faultDetected = false;
                    break;
                }

                if (i >= numberOfPrimaryInputs) {
                    i--;
                    continue;
                }

                Wire primaryInput = (Wire) primaryInputs.elementAt(i);
                if (status[i] == 2) {
                    implyStack.pop();
                    status[i] = 0;
                    i--;
                    continue;
                } else {
                    status[i]++;
                }

                if (status[i] == 1) {
                    Vector events = new Vector();
                    primaryInput.resetValue($0);
                    implyStack.push(primaryInput);

                    simulator = new Simulator(circuit, faults, false, true);

                    Vector evts = new Vector();
                    for (Iterator stackIter = implyStack.iterator(); stackIter.hasNext(); ) {
                        Wire w = (Wire) stackIter.next();
                        evts.add(new Event(w, 0, w.getValue()));
                    }

                    simulator.setPodemFault(fault);
                    events = simulator.simulate(evts);
                    if (events == null) {
                        status[i] = 2;
                    } else if (isDetected(events)) {
                        break;
                    }
                }

                if (status[i] == 2) {
                    implyStack.pop();
                    primaryInput.resetValue($1);
                    implyStack.push(primaryInput);

                    simulator = new Simulator(circuit, faults, false, true);

                    Vector evts = new Vector();
                    for (Iterator stackIter = implyStack.iterator(); stackIter.hasNext(); ) {
                        Wire w = (Wire) stackIter.next();
                        evts.add(new Event(w, 0, w.getValue()));
                    }

                    simulator.setPodemFault(fault);
                    Vector events = simulator.simulate(evts);

                    if (events == null) {
                        implyStack.pop();
                        status[i] = 0;
                        i--;
                        continue;
                    } else if (isDetected(events)) {
                        break;
                    }
                }

                i++;
            }

            report.append($TAB + $LESS + $FAULT + $BLANK + $NAME + $EQUAL + $DOUBLE_QUOT
                    + fault + $DOUBLE_QUOT + $GREATER + $NEW_LINE);

            if (faultDetected == true) {

                Vector testVector = new Vector();

                report.append($TAB + $TAB + $LESS + $TEST_VECTOR + $GREATER + $NEW_LINE);
                report.append($TAB + $TAB + $TAB + $OPEN_BRACKET);
                for (Iterator stackIter = implyStack.iterator(); stackIter.hasNext(); ) {
                    Wire w = (Wire) stackIter.next();
                    report.append(w.getName() + $EQUAL + w.getValue() + $BLANK);
                    String value = w.getValue();
                    if (w.getValue().equals($D)) {
                        value = $1;
                    } else if (w.getValue().equals($D_BAR)) {
                        value = $0;
                    }
                    testVector.add(new Event(w, 0, value));
                }
                for (Iterator iter = primaryInputs.iterator(); iter.hasNext(); ) {
                    Wire input = (Wire) iter.next();
                    if (!implyStack.contains(input)) {
                        report.append(input.getName() + $EQUAL + $0 + $OR + $1 + $BLANK);
                        testVector.add(new Event(input, 0, $0));
                    }
                }
                report.deleteCharAt(report.length() - 1);
                report.append($CLOSE_BRACKET + $NEW_LINE);
                report.append($TAB + $TAB + $LESS + $SLASH + $TEST_VECTOR + $GREATER + $NEW_LINE);

                simulator = new Simulator(circuit, faults, true, false);
                Vector events = simulator.simulate(new Vector(testVector));

                Vector recent = new Vector();
                Vector detected = new Vector();
                for (int j = events.size() - 1; j >= 0; j--) {
                    Event evt = (Event) events.elementAt(j);
                    Wire wire = evt.getWire();
                    if (primaryOutputs.contains(wire)) {
                        if (recent.contains(wire)) {
                            continue;
                        }
                        recent.add(wire);
                        detected = Vectors.union(detected, evt.getFaults());
                        faults = Vectors.minus(faults, evt.getFaults());
                    }
                }

                if (detected.size() > 0) {
                    report.append($TAB + $TAB + $LESS + $ALSO_DETECTED + $GREATER + $NEW_LINE);
                    report.append($TAB + $TAB + $TAB + $OPEN_BRACKET);
                    for (Iterator iter = detected.iterator(); iter.hasNext(); ) {
                        Fault f = (Fault) iter.next();
                        report.append(f + $COMMA + $BLANK);
                        remaining--;
                    }
                    report.deleteCharAt(report.length() - 1);
                    report.deleteCharAt(report.length() - 1);
                    report.append($CLOSE_BRACKET + $NEW_LINE);
                    report.append($TAB + $TAB + $LESS + $SLASH + $ALSO_DETECTED + $GREATER + $NEW_LINE);
                }

                remaining--;
            } else {
                report.append($TAB + $TAB + $NO_TEST + $NEW_LINE);
            }

            double FC = (double) (initialSize - remaining) / (double) initialSize;
            report.append($TAB + $TAB + $LESS + $FAULT_COVERAGE + $BLANK + $VALUE + $EQUAL
                    + $DOUBLE_QUOT + FC + $DOUBLE_QUOT + $BLANK + $SLASH + $GREATER + $NEW_LINE);

            report.append($TAB + $LESS + $SLASH + $FAULT + $GREATER + $NEW_LINE);
        }

        report.append($LESS + $SLASH + $PODEM + $GREATER);
    }

    private boolean isDetected(Vector events) {
        Vector recent = new Vector();
        boolean flag = false;

        for (int i = events.size() - 1; i >= 0; i--) {
            Event evt = (Event) events.elementAt(i);
            Wire wire = evt.getWire();
            if (primaryOutputs.contains(wire)) {
                if (recent.contains(wire)) {
                    continue;
                }

                if (evt.getValue().equals($D) || evt.getValue().equals($D_BAR)) {
                    // a test vector generated...
                    flag = true;
                }
                recent.add(wire);
            }
        }

        return flag;
    }

    public StringBuffer getReport() {
        return report;
    }
}
