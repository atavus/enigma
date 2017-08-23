package org.atavus.enigma;

public class Rotor1 implements Wiring, Cloneable, Rotor {

    private Wiring input;
    private Wiring output;

    private int[] forward = new int[26];
    private int[] reverse = new int[26];

    private int ring;

    private int offset;

    private int notch1 = 26;
    private int notch2 = 26;

    private boolean debug;

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#clone()
     */
    @Override
    public Rotor1 clone() {
        try {
            return (Rotor1) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Failed to clone", e);
        }
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#setDebug(boolean)
     */
    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Rotor1(final char[] wiring) {
        for (int pin = 0; pin < 26; pin++) {
            forward[pin] = wiring[pin] - 'A';
            reverse[wiring[pin] - 'A'] = pin;
        }
        if (wiring.length > 26) {
            notch1 = wiring[26] - 'A';
        }
        if (wiring.length > 27) {
            notch2 = wiring[27] - 'A';
        }
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#advance()
     */
    @Override
    public boolean advance() {
        boolean turnover = (offset == notch1 || offset == notch2);
        offset = (offset + 1) % 26;
        return turnover;
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#setRing(int)
     */
    @Override
    public void setRing(final int ring) {
        this.ring = ring - 1;
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#setRing(char)
     */
    @Override
    public void setRing(final char ring) {
        this.ring = ring - 'A';
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#getOffset()
     */
    @Override
    public char getOffset() {
        return (char) (offset + 'A');
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#setOffset(int)
     */
    @Override
    public void setOffset(final int offset) {
        this.offset = offset - 1;
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#setOffset(char)
     */
    @Override
    public void setOffset(final char offset) {
        this.offset = offset - 'A';
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#setInput(org.atavus.enigma.Wiring)
     */
    @Override
    public void setInput(final Wiring input) {
        if (input == this)
            throw new IllegalArgumentException("Cannot set input to self");
        this.input = input;
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#setOutput(org.atavus.enigma.Wiring)
     */
    @Override
    public void setOutput(final Wiring output) {
        if (output == this)
            throw new IllegalArgumentException("Cannot set output to self");
        this.output = output;
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#forward(int)
     */
    @Override
    public void forward(final int pin) {
        int out = (forward[(pin + 52 - ring + offset) % 26] + 52 - offset + ring) % 26;
        if (debug)
            System.out.format("Rotor forward %c -> %c\n", (pin + 'A'), (out + 'A'));
        output.forward(out);
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#reverse(int)
     */
    @Override
    public void reverse(final int pin) {
        int out = (reverse[(pin + 52 - ring + offset) % 26] + 52 - offset + ring) % 26;
        if (debug)
            System.out.format("Rotor reverse %c -> %c\n", (pin + 'A'), (out + 'A'));
        input.reverse(out);
    }

    /* (non-Javadoc)
     * @see org.atavus.enigma.Rotor#toString()
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(String.format("%c", (ring + 'A')));
        for (int pin : forward) {
            out.append(" ");
            out.append((char) (pin + 'A'));
        }
        out.append(String.format(" %c", (offset + 'A')));
        out.append(String.format(" %c", (notch1 + 'A')));
        out.append(String.format(" %c", (notch2 + 'A')));
        return out.toString();
    }

}