package org.atavus.enigma;

import java.util.Random;
import java.util.Scanner;

import org.atavus.enigma.Machines.Roman;
import org.atavus.enigma.Machines.Type;

public class Configuration {

    private int reflector_number = 0, etw_number = 0;
    private int rotor1_number = 0, rotor2_number = 1, rotor3_number = 2;

    private int ring1_number = 1, ring2_number = 1, ring3_number = 1;
    private int ring1_offset = 1, ring2_offset = 1, ring3_offset = 1;

    private Steckerboard board = new Steckerboard();
    private Keyboard kbd = new Keyboard();

    private Rotor rotor1;
    private Rotor rotor2;
    private Rotor rotor3;
    private Reflector reflector;
    private ETW etw;

    public final Machines.Type type;

    public Configuration(final Machines.Type type) {
        this.type = type;
    }

    public Configuration(final Type type, final int reflector_number, final int etw_number, final String config_string) {
        this.type = type;
        try (Scanner scanner = new Scanner(config_string);) {
            if (scanner.hasNextLine()) {
                reflector_number(reflector_number);
                etw_number(etw_number);
                rotor1_number(scanner.next());
                rotor2_number(scanner.next());
                rotor3_number(scanner.next());
                scanner.next(); // skip the bar
                String rings = scanner.next();
                setRings(rings);
                scanner.next();
                for (String pins = scanner.next(); !"|".equals(pins); pins = scanner.next()) {
                    setPlugboard(pins);
                }
            }
        }
    }

    public Configuration(final Machines.Type type, final Random random) {
        this.type = type;
        randomise(random);
    }

    public void randomise(final Random random) {
        reflector_number(random.nextInt(Machines.getMachines().countReflectors(type)));
        etw_number(random.nextInt(Machines.getMachines().countETWs(type)));
        int rotor_number = random.nextInt(Machines.getMachines().countRotors(type));
        rotor1_number(rotor_number);
        while (rotor_number == rotor1_number)
            rotor_number = random.nextInt(Machines.getMachines().countRotors(type));
        rotor2_number(rotor_number);
        while (rotor_number == rotor1_number || rotor_number == rotor2_number)
            rotor_number = random.nextInt(Machines.getMachines().countRotors(type));
        rotor3_number(rotor_number);
        ring1_number(random.nextInt(26) + 1);
        ring2_number(random.nextInt(26) + 1);
        ring3_number(random.nextInt(26) + 1);
        ring1_offset(random.nextInt(26) + 1);
        ring2_offset(random.nextInt(26) + 1);
        ring3_offset(random.nextInt(26) + 1);
        board.random(random, 10);
    }

    public void reflector_number(int reflector_number) {
        int max = Machines.getMachines().countReflectors(type);
        if (reflector_number < 0 || reflector_number >= max)
            throw new IllegalArgumentException(
                            String.format("Reflector [%d] is not valid, valid values are 0<=n<%d", reflector_number, max));
        this.reflector_number = reflector_number;
        this.reflector = Machines.getMachines().getReflector(type, reflector_number);
    }

    public void etw_number(int etw_number) {
        int max = Machines.getMachines().countETWs(type);
        if (etw_number < 0 || etw_number >= max)
            throw new IllegalArgumentException(String.format("ETW [%d] is not valid, valid values are 0<=n<%d", etw_number, max));
        this.etw_number = etw_number;
        this.etw = Machines.getMachines().getETW(type, etw_number);
    }

    public void rotor1_number(int rotor_number) {
        int max = Machines.getMachines().countRotors(type);
        if (rotor_number < 0 || rotor_number >= max)
            throw new IllegalArgumentException(String.format("Rotor 1 [%d] is not valid, valid values are 0<=n<%d", rotor_number, max));
        rotor1_number = rotor_number;
        rotor1 = Machines.getMachines().getRotor(type, rotor_number);
    }

    public void rotor1_number(String rotor_name) {
        int rotor_number = Roman.valueOf(rotor_name).ordinal();
        int max = Machines.getMachines().countRotors(type);
        if (rotor_number < 0 || rotor_number >= max)
            throw new IllegalArgumentException(String.format("Rotor 1 [%s] is not valid, valid values are 0<=n<%d", rotor_name, max));
        rotor1_number = rotor_number;
        rotor1 = Machines.getMachines().getRotor(type, rotor1_number);
    }

    public void rotor2_number(int rotor_number) {
        int max = Machines.getMachines().countRotors(type);
        if (rotor_number < 0 || rotor_number >= max)
            throw new IllegalArgumentException(String.format("Rotor 2 [%d] is not valid, valid values are 0<=n<%d", rotor_number, max));
        rotor2_number = rotor_number;
        rotor2 = Machines.getMachines().getRotor(type, rotor_number);
    }

    public void rotor2_number(String rotor_name) {
        int rotor_number = Roman.valueOf(rotor_name).ordinal();
        int max = Machines.getMachines().countRotors(type);
        if (rotor_number < 0 || rotor_number >= max)
            throw new IllegalArgumentException(String.format("Rotor 2 [%s] is not valid, valid values are 0<=n<%d", rotor_name, max));
        rotor2_number = rotor_number;
        rotor2 = Machines.getMachines().getRotor(type, rotor2_number);
    }

    public void rotor3_number(int rotor_number) {
        int max = Machines.getMachines().countRotors(type);
        if (rotor_number < 0 || rotor_number >= max)
            throw new IllegalArgumentException(String.format("Rotor 3 [%d] is not valid, valid values are 0<=n<%d", rotor_number, max));
        rotor3_number = rotor_number;
        rotor3 = Machines.getMachines().getRotor(type, rotor_number);
    }

    public void rotor3_number(String rotor_name) {
        int rotor_number = Roman.valueOf(rotor_name).ordinal();
        int max = Machines.getMachines().countRotors(type);
        if (rotor_number < 0 || rotor_number >= max)
            throw new IllegalArgumentException(String.format("Rotor 3 [%d] is not valid, valid values are 0<=n<%d", rotor_name, max));
        rotor3_number = rotor_number;
        rotor3 = Machines.getMachines().getRotor(type, rotor3_number);
    }

    public void ring1_number(int ring_number) {
        if (ring_number < 1 || ring_number > 26)
            throw new IllegalArgumentException(String.format("Invalid ring 1 number [%d], valid values 1..26", ring_number));
        ring1_number = ring_number;
        rotor1.setRing(ring_number);
    }

    public void ring1_number(char ring_number) {
        if (ring_number < 'A' || ring_number > 'Z')
            throw new IllegalArgumentException(String.format("Invalid ring 1 number [%c], valid values A..Z", ring_number));
        ring1_number = ring_number - 'A' + 1;
        rotor1.setRing(ring1_number);
    }

    public void ring2_number(int ring_number) {
        if (ring_number < 1 || ring_number > 26)
            throw new IllegalArgumentException(String.format("Invalid ring 2 number [%d], valid values 1..26", ring_number));
        ring2_number = ring_number;
        rotor2.setRing(ring_number);
    }

    public void ring2_number(char ring_number) {
        if (ring_number < 'A' || ring_number > 'Z')
            throw new IllegalArgumentException(String.format("Invalid ring 2 number [%c], valid values A..Z", ring_number));
        ring2_number = ring_number - 'A' + 1;
        rotor2.setRing(ring2_number);
    }

    public void ring3_number(int ring_number) {
        if (ring_number < 1 || ring_number > 26)
            throw new IllegalArgumentException(String.format("Invalid ring 3 number [%d], valid values 1..26", ring_number));
        ring3_number = ring_number;
        rotor3.setRing(ring_number);
    }

    public void ring3_number(char ring_number) {
        if (ring_number < 'A' || ring_number > 'Z')
            throw new IllegalArgumentException(String.format("Invalid ring 3 number [%c], valid values A..Z", ring_number));
        ring3_number = ring_number - 'A' + 1;
        rotor3.setRing(ring3_number);
    }

    public void ring1_offset(int ring_offset) {
        if (ring_offset < 1 || ring_offset > 26)
            throw new IllegalArgumentException(String.format("Invalid ring 1 offset [%d], valid values 1..26", ring_offset));
        ring1_offset = ring_offset;
        rotor1.setOffset(ring1_offset);
    }

    public void ring1_offset(char ring_offset) {
        if (ring_offset < 'A' || ring_offset > 'Z')
            throw new IllegalArgumentException(String.format("Invalid ring 1 offset [%c], valid values A..Z", ring_offset));
        ring1_offset = ring_offset - 'A' + 1;
        rotor1.setOffset(ring1_offset);
    }

    public void ring2_offset(int ring_offset) {
        if (ring_offset < 1 || ring_offset > 26)
            throw new IllegalArgumentException(String.format("Invalid ring 2 offset [%d], valid values 1..26", ring_offset));
        ring2_offset = ring_offset;
        rotor2.setOffset(ring2_offset);
    }

    public void ring2_offset(char ring_offset) {
        if (ring_offset < 'A' || ring_offset > 'Z')
            throw new IllegalArgumentException(String.format("Invalid ring 2 offset [%c], valid values A..Z", ring_offset));
        ring2_offset = ring_offset - 'A' + 1;
        rotor2.setOffset(ring2_offset);
    }

    public void ring3_offset(int ring_offset) {
        if (ring_offset < 1 || ring_offset > 26)
            throw new IllegalArgumentException(String.format("Invalid ring 3 offset [%d], valid values 1..26", ring_offset));
        ring3_offset = ring_offset;
        rotor3.setOffset(ring3_offset);
    }

    public void ring3_offset(char ring_offset) {
        if (ring_offset < 'A' || ring_offset > 'Z')
            throw new IllegalArgumentException(String.format("Invalid ring 3 offset [%c], valid values A..Z", ring_offset));
        ring3_offset = ring_offset - 'A' + 1;
        rotor3.setOffset(ring3_offset);
    }

    private void create() {
        if (rotor1 == null)
            rotor1 = Machines.getMachines().getRotor(type, rotor1_number);
        if (rotor2 == null)
            rotor2 = Machines.getMachines().getRotor(type, rotor2_number);
        if (rotor3 == null)
            rotor3 = Machines.getMachines().getRotor(type, rotor3_number);
        if (reflector == null)
            reflector = Machines.getMachines().getReflector(type, reflector_number);
        if (etw == null)
            etw = Machines.getMachines().getETW(type, etw_number);
    }

    public void build() {

        // create components that are missing
        create();

        // build the machine configuration
        kbd.setOutput(board);
        board.setInput(kbd);
        board.setOutput(etw);
        etw.setInput(board);
        etw.setOutput(rotor3);
        rotor3.setInput(etw);
        rotor3.setOutput(rotor2);
        rotor2.setInput(rotor3);
        rotor2.setOutput(rotor1);
        rotor1.setInput(rotor2);
        rotor1.setOutput(reflector);
        reflector.setInput(rotor1);

    }

    public void reset() {
        rotor1.setOffset(ring1_offset);
        rotor2.setOffset(ring2_offset);
        rotor3.setOffset(ring3_offset);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        Roman[] romans = Roman.values();
        out.append(String.format("| %s %s %s | %02d %02d %02d |", romans[rotor1_number], romans[rotor2_number], romans[rotor3_number],
                        ring1_number, ring2_number, ring3_number));
        out.append(String.format("%s |", board.toStringSet()));
        out.append(String.format(" %c%c%c |", (ring1_offset + 'A' - 1), (ring2_offset + 'A' - 1), (ring3_offset + 'A' - 1)));
        out.append(String.format(" %c |", (reflector_number + 'A')));
        out.append(String.format(" %c |", (etw_number + 'A')));
        out.append(String.format(" %s |", type));
        return out.toString();
    }

    public void setRings(String rings) {
        ring1_number(rings.charAt(0));
        ring2_number(rings.charAt(1));
        ring3_number(rings.charAt(2));
    }

    public void setRing(char ring1, char ring2, char ring3) {
        ring1_number(ring1);
        ring2_number(ring2);
        ring3_number(ring3);
    }

    public void setPlugboard(String pins) {
        setPlugboard(pins.charAt(0), pins.charAt(1));
    }

    public void setPlugboard(char pin1, char pin2) {
        board.plug(pin1, pin2);
    }

    public void setOffsets(String offsets) {
        ring1_offset(offsets.charAt(0));
        ring2_offset(offsets.charAt(1));
        ring3_offset(offsets.charAt(2));
    }

    public void setOffsets(char... offsets) {
        ring1_offset(offsets[0]);
        ring2_offset(offsets[1]);
        ring3_offset(offsets[2]);
    }

    public String getOffsets() {
        return String.format("%c%c%c", rotor1.getOffset(), rotor2.getOffset(), rotor3.getOffset());
    }

    public Steckerboard board() {
        return board;
    }

    public Keyboard kbd() {
        return kbd;
    }

    public Rotor rotor1() {
        return rotor1;
    }

    public Rotor rotor2() {
        return rotor2;
    }

    public Rotor rotor3() {
        return rotor3;
    }

    public Reflector reflector() {
        return reflector;
    }

    public Rotor etw() {
        return etw;
    }

}