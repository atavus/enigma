package org.atavus.enigma;

public class Keyboard implements Wiring {

    private Wiring output;
    private int out;
    private boolean debug;

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setOutput(final Wiring output) {
        this.output = output;
    }

    @Override
    public void forward(int pin) {
        if (debug)
            System.out.format("Keyboard forward %c\n", (pin + 'A'));
        this.output.forward(pin);
    }

    @Override
    public void reverse(int pin) {
        if (debug)
            System.out.format("Keyboard reverse %c\n", (pin + 'A'));
        this.out = pin;
    }

    public char encode(char ch) {
        forward(ch - 'A');
        return (char) (out + 'A');
    }

}