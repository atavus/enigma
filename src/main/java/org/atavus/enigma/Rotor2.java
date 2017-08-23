package org.atavus.enigma;

public class Rotor2 implements Rotor, Wiring, Cloneable {

    private Wiring input;
    private Wiring output;

    private int[] forward = new int[26];
    private int[] reverse = new int[26];
    private int[] forwardmap = new int[17576];
    private int[] reversemap = new int[17576];

    private int ring;

    private int offset;

    private int notch1 = 26;
    private int notch2 = 26;

    private boolean debug;

    @Override
    public Rotor2 clone() {
        try {
            return (Rotor2) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Failed to clone", e);
        }
    }

    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Rotor2(final char[] wiring) {
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
        for (int pin = 0; pin < 26; pin++) {
            for (int ring = 0; ring < 26; ring++) {
                for (int ofs = 0; ofs < 26; ofs++) {
                    forwardmap[pin + ring * 26 + ofs * 676] = (forward[(pin + 52 - ring + ofs) % 26] + 52 - ofs + ring) % 26;
                    reversemap[pin + ring * 26 + ofs * 676] = (reverse[(pin + 52 - ring + ofs) % 26] + 52 - ofs + ring) % 26;
                }
            }
        }
    }

    @Override
    public boolean advance() {
        boolean turnover = (offset == notch1 || offset == notch2);
        offset = (offset + 1) % 26;
        return turnover;
    }

    /**
     * Ringstellung
     * 
     * @param ring
     *            1=A, 2=B, ...
     */
    @Override
    public void setRing(final int ring) {
        this.ring = ring - 1;
    }

    /**
     * Ringstellung
     * 
     * @param ring
     *            1=A, 2=B, ...
     */
    @Override
    public void setRing(final char ring) {
        this.ring = ring - 'A';
    }

    @Override
    public char getOffset() {
        return (char) (offset + 'A');
    }

    /**
     * Grundstellung
     * 
     * @param offset
     *            1=A, 2=B, ...
     */
    @Override
    public void setOffset(final int offset) {
        this.offset = offset - 1;
    }

    /**
     * Grundstellung
     * 
     * @param offset
     *            1=A, 2=B, ...
     */
    @Override
    public void setOffset(final char offset) {
        this.offset = offset - 'A';
    }

    @Override
    public void setInput(final Wiring input) {
        if (input == this)
            throw new IllegalArgumentException("Cannot set input to self");
        this.input = input;
    }

    @Override
    public void setOutput(final Wiring output) {
        if (output == this)
            throw new IllegalArgumentException("Cannot set output to self");
        this.output = output;
    }

    @Override
    public void forward(final int pin) {
        int out = forwardmap[pin + ring * 26 + offset * 676];
        if (debug)
            System.out.format("Rotor forward %c -> %c\n", (pin + 'A'), (out + 'A'));
        output.forward(out);
    }

    @Override
    public void reverse(final int pin) {
        int out = reversemap[pin + ring * 26 + offset * 676];
        if (debug)
            System.out.format("Rotor reverse %c -> %c\n", (pin + 'A'), (out + 'A'));
        input.reverse(out);
    }

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