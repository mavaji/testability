package testsystem;


import atpg.PODEM;
import atpg.PseudoRandomTG;
import circuit.Circuit;
import util.CircuitGenerator;
import util.Symbols;
import util.parser.VerilogParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class TestSystem implements Symbols {
    private String vFilePath;
    private String reportFilePath;
    private double randomTestFCRequirment;

    public TestSystem(String vFilePath, String reportFilePath, double randomTestFCRequirment) {
        this.vFilePath = vFilePath;
        this.reportFilePath = reportFilePath;
        this.randomTestFCRequirment = randomTestFCRequirment;
    }

    public String getvFilePath() {
        return vFilePath;
    }

    public double getRandomTestFCRequirment() {
        return randomTestFCRequirment;
    }

    public void testAndMakeReport() {
        VerilogParser verilogParser = new VerilogParser(vFilePath);
        verilogParser.parse();
        Vector gates = verilogParser.getLogicGateItems();

        CircuitGenerator generator = new CircuitGenerator(gates);
        Circuit circuit = generator.generate();

        PseudoRandomTG randomTG = new PseudoRandomTG(circuit, randomTestFCRequirment);
        Vector remaining = randomTG.execute();

        PODEM podem = new PODEM(circuit, remaining);
        podem.execute();

        StringBuffer report = new StringBuffer();
        report.append($LESS + $TEST_GENERATION + $GREATER + $NEW_LINE);

        String circuitStr = circuit.toString();
        circuitStr = circuitStr.replaceAll($NEW_LINE, $NEW_LINE + $TAB);
        circuitStr = $TAB + circuitStr;

        String rndStr = randomTG.getReport().toString();
        rndStr = rndStr.replaceAll($NEW_LINE, $NEW_LINE + $TAB);
        rndStr = $TAB + rndStr;

        String podemStr = podem.getReport().toString();
        podemStr = podemStr.replaceAll($NEW_LINE, $NEW_LINE + $TAB);
        podemStr = $TAB + podemStr;

        report.append(circuitStr + $NEW_LINE);
        report.append(rndStr + $NEW_LINE);
        report.append(podemStr + $NEW_LINE);

        report.append($LESS + $SLASH + $TEST_GENERATION + $GREATER);

        StringTokenizer tokenizer = new StringTokenizer(report.toString(), $NEW_LINE);
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(reportFilePath));
            if (tokenizer.hasMoreTokens()) {
                writer.write(tokenizer.nextToken());
            }
            while (tokenizer.hasMoreTokens()) {
                writer.newLine();
                writer.write(tokenizer.nextToken());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
