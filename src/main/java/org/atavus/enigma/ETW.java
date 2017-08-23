package org.atavus.enigma;

public class ETW extends Rotor1 {

    public ETW(char[] wiring) {
        super(wiring);
    }

    @Override
    public ETW clone() {
        return (ETW) super.clone();
    }

}
