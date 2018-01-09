package util;

/**
 * Keeps all of the symbols (characters) that might be needed
 * during parse operation or other parts of the program.
 * Symbols are sorted alphanumerically in increasing order.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public interface Symbols {
    /**
     * One of 9-Value logic meaning 1.
     */
    String $1 = "1";
    /**
     * One of 9-Value logic meaning 0.
     */
    String $0 = "0";
    /**
     * One of 9-Value logic meaning U or undefined.
     */
    String $U = "U";
    /**
     * One of 9-Value logic meaning 1/0 (fault-free = 1, faulty = 0).
     */
    String $D = "D";
    /**
     * One of 9-Value logic meaning 0/1 (fault-free = 0, faulty = 1).
     */
    String $D_BAR = "D_BAR";
    /**
     * One of 9-Value logic meaning 0/U (fault-free = 0, faulty = U).
     */
    String $G0 = "G0";
    /**
     * One of 9-Value logic meaning 1/U (fault-free = 1, faulty = U).
     */
    String $G1 = "G1";
    /**
     * One of 9-Value logic meaning U/0 (fault-free = U, faulty = 0).
     */
    String $F0 = "F0";
    /**
     * One of 9-Value logic meaning U/1 (fault-free = U, faulty = 1).
     */
    String $F1 = "F1";

    char _OPEN_PARENTHESIS = '(';

    char _SHARP = '#';

    String $ALL_FAULTS = "all_faults";

    String $ALSO_DETECTED = "also_detected";

    String $ANONYMOUS = "anonymous";

    String $AT_SIGN = "@";

    String $AVERAGE = "average";

    String $BLANK = " ";

    String $CIRCUIT = "circuit";

    String $CLOSE_BRACKET = "]";

    String $CLOSE_PARENTHESIS = ")";

    String $COLLAPSED_FAULTS = "collapsed_faults";

    String $COLON = ":";

    String $COMMA = ",";

    String $DASH = "-";

    String $DELAY = "delay";

    String $DEDUCTION = "deduction";

    String $DETECTED_FAULTS = "detected_faults";

    String $DOUBLE_QUOT = "\"";

    String $EMPTY = "";

    String $EQUAL = "=";

    String $EVENT = "event";

    String $EVENTS = "events";

    String $FALL = "fall";

    String $FAULT = "fault";

    String $FAULT_COVERAGE = "fault_coverage";

    String $FAULT_COVERAGE_REQUIRED = "fault_coverage_required";

    String $GREATER = ">";

    String $INERTIAL = "inertial";

    String $INPUTS = "inputs";

    String $LESS = "<";

    String $LEVEL = "level";

    String $LOGIC_GATE = "logic_gate";

    String $MAX = "max";

    String $MIN = "min";

    String $NAME = "name";

    String $NEW_LINE = "\n";

    String $NO_TEST = "No test can be generated";

    String $OPEN_BRACKET = "[";

    String $OPEN_PARENTHESIS = "(";

    String $OR = "|";

    String $OUTPUT = "output";

    String $PODEM = "PODEM";

    String $PSEUDO_RANDOM_TEST_GEBERATION = "pseudo_random_test_generation";

    String $RANDOM_TEST_VECTOR = "random_test_vector";

    String $REMAINING_FAULTS = "remaining_faults";

    String $RISE = "rise";

    String $SHARP = "#";

    String $SIZE = "size";

    String $SLASH = "/";

    String $TAB = "\t";

    String $TEST_GENERATION = "test_generation";

    String $TEST_VECTOR = "test_vector";

    String $TIME = "time";

    String $TRANSPORT = "transport";

    String $TURN_OFF = "turn_off";

    String $TYP = "typ";

    String $TYPE = "type";

    String $UNDER_LINE = "_";

    String $VALUE = "value";

    String $WIRE = "wire";
}
