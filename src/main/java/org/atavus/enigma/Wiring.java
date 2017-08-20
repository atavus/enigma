package org.atavus.enigma;

public interface Wiring {
    public void forward(int pin);

    public void reverse(int pin);
}