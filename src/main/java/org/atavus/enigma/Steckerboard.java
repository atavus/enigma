package org.atavus.enigma;

import java.util.Random;

public class Steckerboard implements Wiring {

    private Wiring input;
    private Wiring output;

    private int[] forward = new int[26];

    private boolean debug;

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Steckerboard() {
        reset();
    }

    public void reset() {
        for (int pin = 0; pin < 26; pin++) {
            forward[pin] = pin;
        }
    }

    public void random(final Random random, final int pairs) {
        for (int pin = 0; pin < 26; pin++) {
            forward[pin] = -1;
        }
        int sets = 0;
        while (sets < pairs) {
            int pin1 = random.nextInt(26);
            int pin2 = random.nextInt(26);
            if (pin1 != pin2 && forward[pin1] == -1 && forward[pin2] == -1) {
                forward[pin1] = pin2;
                forward[pin2] = pin1;
                sets++;
            }
        }
        for (int pin = 0; pin < 26; pin++) {
            if (forward[pin] == -1)
                forward[pin] = pin;
        }
    }

    @Override
    public void forward(int pin) {
        if (debug)
            System.out.format("Steckerboard forward %c -> %c\n", (pin + 'A'), (forward[pin] + 'A'));
        output.forward(forward[pin]);
    }

    @Override
    public void reverse(int pin) {
        if (debug)
            System.out.format("Steckerboard reverse %c -> %c\n", (pin + 'A'), (forward[pin] + 'A'));
        input.reverse(forward[pin]);
    }

    public void setInput(Wiring input) {
        this.input = input;
    }

    public void setOutput(Wiring output) {
        this.output = output;
    }

    public void plug(char pin1, char pin2) {
        forward[pin1 - 'A'] = pin2 - 'A';
        forward[pin2 - 'A'] = pin1 - 'A';
    }

    public String toStringSet() {
        StringBuilder out = new StringBuilder();
        for (int pin = 0; pin < forward.length; pin++) {
            if (forward[pin] > pin) {
                out.append(" ");
                out.append((char) (pin + 'A'));
                out.append((char) (forward[pin] + 'A'));
            }
        }
        return out.toString();
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