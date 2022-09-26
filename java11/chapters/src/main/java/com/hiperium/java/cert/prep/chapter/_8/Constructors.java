package com.hiperium.java.cert.prep.chapter._8;

/**
 * Each existing constructors must initialize all final instance variables except all the declared
 * in the instance initialization blocks.
 */
public class Constructors {

    private final int volume;
    private final String type;

    {
        this.volume = 10;
    }

    public Constructors(String type) {
        this.type = type;
    }

    /**
     * IF WE CREATE THIS CONSTRUCTOR, "type" VAR MUST BE INITIALIZED.
     */
    public Constructors() {
        // this.volume = 10; ERROR ===>>> variable "volume" has already been initialized.
        // this("happy");       OK ===>>> this works but, we need to comment out the next line.
        this.type = "happy";
    }
}
