package org.atavus.enigma;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.atavus.enigma.mini.EnigmaMini;
import org.junit.Test;

public class EnigmaMiniTest {

    @Test
    public void testSpeed() {
        char[] input = "NQVLT YQFSE WWGJZ GQHVS EIXIM YKCNW IEBMB ATPPZ TDVCU PKAY".toCharArray();
        char[] output = new char[input.length];
        EnigmaMini machine = new EnigmaMini();
        for (int repeat = 0; repeat < 100000000; repeat++) {
            machine.randomReflector();
            machine.randomRotors();
            machine.randomSteckerboard(10);
            machine.encode(input, output);
        }
    }

    @Test
    public void testEncipher() {

        EnigmaMini machine = new EnigmaMini();
        machine.setupReflector(EnigmaMini.Reflector.B);
        machine.setupRotor1(EnigmaMini.Roman.IV);
        machine.setupRotor2(EnigmaMini.Roman.II);
        machine.setupRotor3(EnigmaMini.Roman.V);
        machine.setRotor1Ring('G' - 'A');
        machine.setRotor2Ring('M' - 'A');
        machine.setRotor3Ring('Y' - 'A');
        machine.setupSteckerboard("DN", "GR", "IS", "KC", "QX", "TM", "PV", "HY", "FW", "BJ");
        machine.setRotor1Offset('D' - 'A');
        machine.setRotor2Offset('H' - 'A');
        machine.setRotor3Offset('O' - 'A');

        char[] original = "GXS".toCharArray();
        System.out.format("Original %s\n", Arrays.toString(original));
        char[] msgkey = new char[3];
        machine.encode(original, msgkey);
        System.out.format("Encoded  %s\n", Arrays.toString(msgkey));
        machine.setRotor1Offset('D' - 'A');
        machine.setRotor2Offset('H' - 'A');
        machine.setRotor3Offset('O' - 'A');
        char[] verify = new char[3];
        machine.encode(msgkey, verify);
        System.out.format("Verify   %s\n", Arrays.toString(verify));

        assertEquals(Arrays.toString(original), Arrays.toString(verify));
        assertEquals("[R, L, P]", Arrays.toString(msgkey));

    }

}
