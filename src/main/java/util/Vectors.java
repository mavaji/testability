package util;

import util.parser.Keywords;

import java.util.Iterator;
import java.util.Vector;

/**
 * This class is used as a utility to do some operation on vectors.
 *
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class Vectors implements Symbols, Keywords {

    public static Vector minus(Vector v1, Vector v2) {
        if (v1 == null) {
            return new Vector();
        } else if (v1 != null && v2 == null) {
            return new Vector(v1);
        }

        Vector result = new Vector(v1);

        for (Iterator v2Iter = v2.iterator(); v2Iter.hasNext(); ) {
            result.remove(v2Iter.next());
        }

        return result;
    }

    public static Vector union(Vector v1, Vector v2) {
        if (v1 == null && v2 == null) {
            return new Vector();
        } else if (v1 != null && v2 == null) {
            return new Vector(v1);
        } else if (v1 == null && v2 != null) {
            return new Vector(v2);
        }

        Vector result = new Vector(v1);
        Object obj;

        for (Iterator v2Iter = v2.iterator(); v2Iter.hasNext(); ) {
            obj = v2Iter.next();
            if (!result.contains(obj)) {
                result.add(obj);
            }
        }

        return result;
    }

    public static Vector common(Vector v1, Vector v2) {
        Vector result = new Vector();
        if (v1 == null || v2 == null) {
            return result;
        }

        Object obj;

        for (Iterator v2Iter = v2.iterator(); v2Iter.hasNext(); ) {
            obj = v2Iter.next();
            if (v1.contains(obj)) {
                result.add(obj);
            }
        }

        return result;
    }
}

