package org.atavus.enigma;

public class Coupling implements Wiring {

    private Wiring input;
    private Wiring output;

    private int[] cacheforward = new int[26];
    private int pin;

    public void setInput(final Wiring input) {
        if (input == this)
            throw new IllegalArgumentException("Cannot set input to self");
        this.input = input;
    }

    public void setOutput(final Wiring output) {
        if (output == this)
            throw new IllegalArgumentException("Cannot set output to self");
        this.output = output;
    }

    public void cache() {
        for (int pin = 0; pin < 26; pin++) {
            output.forward(pin);
            cacheforward[pin] = this.pin;
        }
    }

    @Override
    public void forward(int pin) {
        input.reverse(cacheforward[pin]);
    }

    @Override
    public void reverse(int pin) {
        this.pin = pin;
    }

}
