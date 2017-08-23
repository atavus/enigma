package org.atavus.enigma;

public interface Rotor extends Wiring {

    Rotor clone();

    void setDebug(boolean debug);

    boolean advance();

    /**
     * Ringstellung
     * 
     * @param ring
     *            1=A, 2=B, ...
     */
    void setRing(int ring);

    /**
     * Ringstellung
     * 
     * @param ring
     *            1=A, 2=B, ...
     */
    void setRing(char ring);

    char getOffset();

    /**
     * Grundstellung
     * 
     * @param offset
     *            1=A, 2=B, ...
     */
    void setOffset(int offset);

    /**
     * Grundstellung
     * 
     * @param offset
     *            1=A, 2=B, ...
     */
    void setOffset(char offset);

    void setInput(Wiring input);

    void setOutput(Wiring output);

}