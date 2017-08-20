package org.atavus.enigma;

public class Reflector implements Wiring, Cloneable {

    private Wiring input;

    private int[] forward = new int[26];

    private boolean debug;

    @Override
    public Reflector clone() {
        try {
            return (Reflector) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Failed to clone", e);
        }
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Reflector(final char[] wiring) {
        for (int pin = 0; pin < 26; pin++) {
            forward[pin] = wiring[pin] - 'A';
        }
    }

    public void setInput(final Wiring input) {
        this.input = input;
    }

    @Override
    public void forward(final int pin) {
        if (debug)
            System.out.format("Reflector %c -> %c\n", (pin + 'A'), (forward[pin] + 'A'));
        input.reverse(forward[pin]);
    }

    @Override
    public void reverse(final int pin) {
        if (debug)
            System.out.format("Reflector %c -> %c\n", (pin + 'A'), (forward[pin] + 'A'));
        input.reverse(forward[pin]);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(" ");
        for (int pin : forward) {
            out.append(" ");
            out.append((char) (pin + 'A'));
        }
        return out.toString();
    }

}