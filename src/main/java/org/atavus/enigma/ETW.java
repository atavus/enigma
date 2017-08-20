package org.atavus.enigma;

public class ETW extends Rotor {

    public ETW(char[] wiring) {
        super(wiring);
    }

    @Override
    public ETW clone() {
        return (ETW) super.clone();
    }

}
