package atpg;

import circuit.Circuit;
import circuit.Event;
import circuit.logicelements.Wire;
import simulation.Simulator;
import util.Symbols;
import util.Vectors;
import util.parser.Keywords;

import java.util.Iterator;
import java.util.Vector;

/**
 * This class does all is needed for a pseudo-random test generation.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class PseudoRandomTG implements Symbols, Keywords {
    /**
     * Circuit that random test generation is to be run on it.
     */
    private Circuit circuit;
    /**
     * A logic & fault simulator that is used to deductive fault simulation.
     */
    private Simulator simulator;
    /**
     * The random test vector that is applied of the circuit.
     */
    private Vector testVector = new Vector();
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
     * Report of events.
     */
    private StringBuffer report = new StringBuffer();
    /**
     * Fault coverage that ther pseudo-random test generation must achive.
     */
    private double faultCoverageThreshold;

    public PseudoRandomTG(Circuit circuit, double faultCoverageThreshold) {
        this.circuit = circuit;
        simulator = new Simulator(circuit, circuit.getCollapsedFaults(), true, false);

        numberOfPrimaryInputs = circuit.getNumberOfPrimaryInputs();
        primaryInputs = circuit.getPrimaryInputs();

        primaryOutputs = circuit.getPrimaryOutputs();

        this.faultCoverageThreshold = faultCoverageThreshold;
    }

    private void createRandomTestVector() {
        Vector tmpTestVector;
        String value;

        boolean isIterated;
        do {
            tmpTestVector = new Vector();
            value = new String();
            isIterated = true;

            for (int i = 0; i < numberOfPrimaryInputs; i++) {
                value = String.valueOf(Math.random() >= 0.5 ? 1 : 0);
                tmpTestVector.add(new Event((Wire) primaryInputs.elementAt(i), 0, value));
            }

            if (testVector.isEmpty()) {
                isIterated = false;
            } else {
                Event tmpEvt;
                Event evt;
                for (int i = 0; i < numberOfPrimaryInputs; i++) {
                    tmpEvt = (Event) tmpTestVector.elementAt(i);
                    evt = (Event) testVector.elementAt(i);
                    if (!(tmpEvt.getWire().equals(evt.getWire()) && tmpEvt.getValue().equals(evt.getValue()))) {
                        isIterated = false;
                        break;
                    }
                }
            }
        } while (isIterated == true);

        testVector = new Vector(tmpTestVector);
    }

    public Vector execute() {
        report.append($LESS + $PSEUDO_RANDOM_TEST_GEBERATION + $BLANK);
        report.append($FAULT_COVERAGE_REQUIRED + $EQUAL
                + $DOUBLE_QUOT + faultCoverageThreshold + $DOUBLE_QUOT + $GREATER + $NEW_LINE);

        int initialSize = circuit.getCollapsedFaults().size();
        Vector faults = circuit.getCollapsedFaults();
        double FC = 0;

        while (FC < faultCoverageThreshold) {
            createRandomTestVector();
            Vector events = simulator.simulate(new Vector(testVector));

            Vector recent = new Vector();
            Vector detected = new Vector();
            for (int i = events.size() - 1; i >= 0; i--) {
                Event evt = (Event) events.elementAt(i);
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
            double newFC = (double) (initialSize - faults.size()) / (double) initialSize;

            if (newFC != FC) {
                FC = newFC;
                updateReport(events, detected, FC);
            }
        }

        report.append($TAB + $LESS + $REMAINING_FAULTS + $BLANK
                + $SIZE + $EQUAL + $DOUBLE_QUOT + faults.size() + $DOUBLE_QUOT + $GREATER + $NEW_LINE);
        report.append($TAB + $TAB + $OPEN_BRACKET);
        for (Iterator iter = faults.iterator(); iter.hasNext(); ) {
            report.append(iter.next() + $COMMA + $BLANK);
        }
        report.deleteCharAt(report.length() - 1);
        report.deleteCharAt(report.length() - 1);
        report.append($CLOSE_BRACKET + $NEW_LINE);
        report.append($TAB + $LESS + $SLASH + $REMAINING_FAULTS + $GREATER + $NEW_LINE);

        report.append($LESS + $SLASH + $PSEUDO_RANDOM_TEST_GEBERATION + $GREATER);

        return faults;
    }

    private void updateReport(Vector events, Vector detected, double FC) {
        report.append($TAB + $LESS + $RANDOM_TEST_VECTOR + $GREATER + $NEW_LINE);

        report.append($TAB + $TAB + $OPEN_BRACKET);
        for (Iterator iter = testVector.iterator(); iter.hasNext(); ) {
            Event evt = (Event) iter.next();
            report.append(evt.getWire().getName() + $EQUAL + evt.getValue() + $BLANK);
        }
        report.deleteCharAt(report.length() - 1);
        report.append($CLOSE_BRACKET + $NEW_LINE);

        report.append($TAB + $TAB + $LESS + $EVENTS + $GREATER + $NEW_LINE);
        for (Iterator iter = events.iterator(); iter.hasNext(); ) {
            String evtStr = ((Event) iter.next()).toString();
            evtStr = evtStr.replaceAll($NEW_LINE, $NEW_LINE + $TAB + $TAB + $TAB);
            evtStr = $TAB + $TAB + $TAB + evtStr;
            report.append(evtStr + $NEW_LINE);
        }
        report.append($TAB + $TAB + $LESS + $SLASH + $EVENTS + $GREATER + $NEW_LINE);

        report.append($TAB + $TAB + $LESS + $DETECTED_FAULTS + $GREATER + $NEW_LINE);
        report.append($TAB + $TAB + $TAB + $OPEN_BRACKET);
        for (Iterator iter = detected.iterator(); iter.hasNext(); ) {
            report.append(iter.next() + $COMMA + $BLANK);
        }
        report.deleteCharAt(report.length() - 1);
        report.deleteCharAt(report.length() - 1);
        report.append($CLOSE_BRACKET + $NEW_LINE);
        report.append($TAB + $TAB + $LESS + $SLASH + $DETECTED_FAULTS + $GREATER + $NEW_LINE);

        report.append($TAB + $TAB + $LESS + $FAULT_COVERAGE + $BLANK + $VALUE + $EQUAL
                + $DOUBLE_QUOT + FC + $DOUBLE_QUOT + $BLANK + $SLASH + $GREATER + $NEW_LINE);

        report.append($TAB + $LESS + $SLASH + $RANDOM_TEST_VECTOR + $GREATER + $NEW_LINE);
    }

    public StringBuffer getReport() {
        return report;
    }
}
