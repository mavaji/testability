package util.parser;

import circuit.Event;
import circuit.delay.Delay;
import circuit.delay.GateDelay;
import circuit.logicelements.Wire;
import util.LogicGateItem;
import util.Symbols;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This class is used to parse a Verilog (like) code.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class VerilogParser implements Symbols, Keywords {
    /**
     * Name (path) of the Verilog code file.
     */
    private String verilogFileName;
    /**
     * A reader that each time reads a compelete line.
     */
    private BufferedReader verilogBufferedReader;
    /**
     * List of logic gate items that are returned as the result of parsing.
     */
    private Vector logicGateItems = new Vector();
    /**
     * List of initial events that is returned as the result of parsing.
     */
    private Vector events = new Vector();

    public VerilogParser(String fileName) {
        this.verilogFileName = fileName;

        try {
            verilogBufferedReader = new BufferedReader(new FileReader(verilogFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses a Verilog (like) code and returns the structure of the circuit as a netlist.
     */
    public void parse() {
        String line = new String();
        StringTokenizer tokenizer;
        String token;

        try {
            while (verilogBufferedReader.ready()) {
                line = verilogBufferedReader.readLine();
                tokenizer = new StringTokenizer(line);
                token = new String();

                if (tokenizer.hasMoreTokens()) {
                    token = tokenizer.nextToken();
                    if (token.equals(__assign)) {
                        parseAssignments(new StringTokenizer(line));
                    } else {
                        parseLogicGateItems(new StringTokenizer(line));
                    }
                } else {
                    continue;
                }
            }
            Collections.sort(events);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseLogicGateItems(StringTokenizer tokenizer) {
        String token = new String();
        LogicGateItem logicGateItem = new LogicGateItem();
        GateDelay gateDelay = null;
        Delay delay = null;

        // reading type of gate.
        if (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            logicGateItem.setType(token);
        } else {
            return;
        }
        if (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
        } else {
            return;
        }

        // reading delay of the gate.
        if (token.charAt(0) == _SHARP) {
            gateDelay = new GateDelay();

            token = token.replaceAll($SHARP, $EMPTY);
            token = token.replaceAll("\\" + $OPEN_PARENTHESIS, $EMPTY);
            token = token.replaceAll("\\" + $CLOSE_PARENTHESIS, $EMPTY);

            StringTokenizer gateDelayTokenizer = new StringTokenizer(token, $COMMA);
            String gateDelayToken;

            // reading rise, fall, turnoff delays.
            for (int j = 1; j <= 3 && gateDelayTokenizer.hasMoreTokens(); j++) {
                gateDelayToken = gateDelayTokenizer.nextToken();

                StringTokenizer delayTokenizer = new StringTokenizer(gateDelayToken, $COLON);
                String delayToken = new String();
                delay = new Delay();

                // reading min, typ, max values of each delay component.
                for (int i = 1; i <= 3 && delayTokenizer.hasMoreTokens(); i++) {
                    delayToken = delayTokenizer.nextToken();
                    switch (i) {
                        case 1:
                            delay.setMinValue(Integer.valueOf(delayToken).intValue());
                            break;
                        case 2:
                            delay.setTypValue(Integer.valueOf(delayToken).intValue());
                            break;
                        case 3:
                            delay.setMaxValue(Integer.valueOf(delayToken).intValue());
                            break;
                    }
                }

                switch (j) {
                    case 1:
                        gateDelay.setRise(delay);
                        break;
                    case 2:
                        gateDelay.setFall(delay);
                        break;
                    case 3:
                        gateDelay.setTurnOff(delay);
                        break;
                }
            }

            logicGateItem.setDelay(gateDelay);

            if (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken();
                if (token.equals($TRANSPORT)) {
                    logicGateItem.getDelay().setTransport(true);
                    if (tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken();
                    }
                }
            }
        }

        // reading name of the gate.
        if (token.charAt(0) != _OPEN_PARENTHESIS) {
            logicGateItem.setName(token);
            if (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken();
            } else {
                return;
            }
        }

        token = token.replaceAll("\\" + $OPEN_PARENTHESIS, $EMPTY);
        token = token.replaceAll("\\" + $CLOSE_PARENTHESIS, $EMPTY);

        // reading output of the gate.
        StringTokenizer inoutTokenizer = new StringTokenizer(token, $COMMA);
        String inoutToken = new String();
        if (inoutTokenizer.hasMoreTokens()) {
            inoutToken = inoutTokenizer.nextToken();
            logicGateItem.setOutput(inoutToken);
        }

        // reading inputs of the gate.
        Vector inputs = new Vector();
        while (inoutTokenizer.hasMoreTokens()) {
            inoutToken = inoutTokenizer.nextToken();
            inputs.add(inoutToken);
        }
        logicGateItem.setInputs(inputs);

        logicGateItems.add(logicGateItem);
    }

    private void parseAssignments(StringTokenizer tokenizer) {
        String token = new String();
        Wire wire = null;
        int time = -1;
        String value = null;

        if (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
        } else {
            return;
        }

        // reading name of wire.
        if (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            wire = new Wire(token);
        }

        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();

            // reading time of the next event.
            if (token.charAt(0) == _SHARP) {
                token = token.replaceAll($SHARP, $EMPTY);
                time = Integer.valueOf(token).intValue();
            }

            // reading value of the next event.
            token = tokenizer.nextToken();
            value = new String(token);

            events.add(new Event(wire, time, value));
        }
    }

    public Vector getLogicGateItems() {
        return logicGateItems;
    }

    public Vector getEvents() {
        return events;
    }
}
