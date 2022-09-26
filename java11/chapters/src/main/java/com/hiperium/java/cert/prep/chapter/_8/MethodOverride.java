package com.hiperium.java.cert.prep.chapter._8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Alpaca {
    protected List<String> hairy(int p) {
        return Collections.emptyList();
    }
}

public class MethodOverride extends Alpaca {

    // List<String> hairy(int p) { return null; }              ===>>> ERROR: Assigning weaker access privileges.
    // public List<CharSequence> hairy(int p) { return null; } ===>>> ERROR: Using incompatible return type.
    // public Object hairy(int p) { return null; }             ===>>> ERROR: Using incompatible return type.
    // public List<String> hairy(int p) { return null; }       ===>>> OK: A broader access its allowed

    /**
     * Remember: Covariant types only apply to return values of overridden methods, not method parameters.
     */
    @Override
    public ArrayList<String> hairy(int p) {
        return null;
    }
}
