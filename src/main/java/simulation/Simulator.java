package simulation;

import circuit.Circuit;
import circuit.Event;
import circuit.Fault;
import circuit.delay.GateDelay;
import circuit.logicelements.Wire;
import circuit.logicelements.logicgates.AbstractLogicGate;
import util.Symbols;
import util.Vectors;
import util.parser.Keywords;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * This class is an all-purpose simulator that can do true-value simulation,
 * fault simulation or even a execute-based simulation.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class Simulator implements Symbols, Keywords {
    /**
     * Structure of the circuit.
     */
    private Circuit circuit;
    /**
     * List of events on the circuit.
     */
    private Vector events;
    /**
     * List of collapsed faults of the circuit.
     */
    private Vector initialFaults;
    /**
     * List of most recently faults induced by deductive method.
     */
    private HashMap recent = new HashMap();
    /**
     * If <code>true</code> a deductive fault simulation is done.
     */
    private boolean isDeductive;
    /**
     * If <code>true</code> a execute-based simulation is done.
     */
    private boolean isPODEM;
    /**
     * Indicated which fault the execute simulation must consider.
     */
    private Fault podemFault;

    public void setPodemFault(Fault podemFault) {
        this.podemFault = podemFault;
    }

    public Simulator(Circuit circuit, Vector initialFaults, boolean isDeductive, boolean isPODEM) {
        this.circuit = circuit;
        this.initialFaults = initialFaults;
        this.isDeductive = isDeductive;
        this.isPODEM = isPODEM;
    }

    public Vector simulate(Vector initialEvents) {
        circuit.reset();
        this.events = initialEvents;

        Vector result = new Vector();

        while (!events.isEmpty()) {
            Event event = (Event) events.remove(0);

            Wire wire = event.getWire();
            int time = event.getTime();
            String value = event.getValue();

            if (isPODEM) {
                if (wire.getName().equals(podemFault.getWireName())) {
                    if (!value.equals(podemFault.getFaultValue())) {
                        if (value.equals($1)) {
                            value = $D;
                        } else if (value.equals($0)) {
                            value = $D_BAR;
                        }
                    }
                }
                event = new Event(wire, time, value);
            }

            if (isDeductive) {
                Vector faults = event.getFaults();
                Vector tempVect = new Vector();
                tempVect.add(value.equals($0) ?
                        new Fault(wire, $1) : new Fault(wire, $0));
                faults = Vectors.union(faults, tempVect);

                tempVect = new Vector(faults);
                for (Iterator fIter = tempVect.iterator(); fIter.hasNext(); ) {
                    Fault fault = (Fault) fIter.next();
                    if (!initialFaults.contains(fault)) {
                        faults.remove(fault);
                    }
                }

                event.setFaults(faults);
                recent.put(wire.getName(), faults);
            }

            if (result.contains(event)) {
                result.remove(event);
            }
            result.add(event);

            for (Iterator rowIter = circuit.getLevels().iterator(); rowIter.hasNext(); ) {
                Vector col = (Vector) rowIter.next();
                for (Iterator colIter = col.iterator(); colIter.hasNext(); ) {
                    AbstractLogicGate logicGate = (AbstractLogicGate) colIter.next();

                    if (logicGate.getInputs().contains(wire)) {
                        for (Iterator inputsIter = logicGate.getInputs().iterator(); inputsIter.hasNext(); ) {
                            Wire input = (Wire) inputsIter.next();
                            if (input.equals(wire)) {
                                input.resetValue(value);
                            }
                        }

                        int t;
                        String function = logicGate.logicFunction();
                        GateDelay gateDelay = logicGate.getDelay();
                        if (function.equals($1)) {
                            t = gateDelay.getRise().getDelay();
                        } else if (function.equals($0)) {
                            t = gateDelay.getFall().getDelay();
                        } else if (function.equals($U)) {
                            t = gateDelay.getTurnoff().getDelay();
                        } else {
                            t = gateDelay.getDelay();
                        }
                        t += time;

                        Wire output = logicGate.getOutput();

                        boolean signalPass = true;

                        if (!gateDelay.isTransport()) {
                            // it is inertial...
                            for (int e = events.size() - 1; e >= 0; e--) {
                                Event evt = (Event) events.elementAt(e);
                                if (evt.getWire().equals(output)) {
                                    if (function.equals($1)) {
                                        if ((t - evt.getTime()) < gateDelay.getRise().getDelay()) {
                                            signalPass = false;
                                        }
                                    } else if (function.equals($0)) {
                                        if ((t - evt.getTime()) < gateDelay.getFall().getDelay()) {
                                            signalPass = false;
                                        }
                                    } else if (function.equals($U)) {
                                        if ((t - evt.getTime()) < gateDelay.getTurnoff().getDelay()) {
                                            signalPass = false;
                                        }
                                    } else {
                                        if ((t - evt.getTime()) < gateDelay.getDelay()) {
                                            signalPass = false;
                                        }
                                    }
                                    break;
                                }
                            }
                        }

                        if (signalPass) {
                            Event evt = new Event(output, t, logicGate.logicFunction());

                            if (isDeductive) {
                                Vector newFaults = logicGate.getOutputFaultList(recent);

                                Vector tempVect = new Vector(newFaults);
                                for (Iterator fIter = tempVect.iterator(); fIter.hasNext(); ) {
                                    Fault fault = (Fault) fIter.next();
                                    if (!initialFaults.contains(fault)) {
                                        newFaults.remove(fault);
                                    }
                                }

                                evt.setFaults(newFaults);
                            }

                            if (events.contains(evt)) {
                                events.remove(evt);
                            }
                            events.add(evt);
                            Collections.sort(events);
                        }
                    }
                }
            }
        }

        // checks for existing of D-frontier...
        if (isPODEM) {
            Vector primaryOutputs = circuit.getPrimaryOutputs();
            Vector recent = new Vector();
            boolean flag = true;

            for (int i = result.size() - 1; i >= 0; i--) {
                Event evt = (Event) result.elementAt(i);
                Wire wire = evt.getWire();
                if (primaryOutputs.contains(wire)) {
                    if (recent.contains(wire)) {
                        continue;
                    }

                    if (evt.getValue().equals($0) || evt.getValue().equals($1)) {
                        flag = false;
                    } else {
                        flag = true;
                        break;
                    }
                    recent.add(wire);
                }
            }

            if (flag == false) {
                return null;
            }
        }

        return result;
    }

}
