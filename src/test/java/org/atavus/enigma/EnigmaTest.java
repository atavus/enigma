package org.atavus.enigma;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class EnigmaTest {

    @Test
    public void testRotor1() {
        // 01234567890123456789012345
        // ABCDEFGHIJKLMNOPQRSTUVWXYZ
        // EKMFLGDQVZNTOWYHXUSPAIBRCJ
        // EKMFLGDQVZNTOWYHXUSPAIBRCJ
        // ABCDEFGHIJKLMNOPQRSTUVWXYZ
        // 01234567890123456789012345
        // A(0) -> E(4)
        // A'(0) -> U'(20)
        Rotor1 rotor = new Rotor1("EKMFLGDQVZNTOWYHXUSPAIBRCJQ".toCharArray());
        Wiring wiring = new Wiring() {

            @Override
            public void forward(int pin) {
                assertEquals(4, pin);
            }

            @Override
            public void reverse(int pin) {
                assertEquals(20, pin);
            }
        };
        rotor.setOutput(wiring);
        rotor.setInput(wiring);
        rotor.forward(0);
        rotor.reverse(0);
    }

    @Test
    public void testRotor1Ring1() {
        // 01234567890123456789012345
        // ABCDEFGHIJKLMNOPQRSTUVWXYZ
        // EKMFLGDQVZNTOWYHXUSPAIBRCJ
        // ABCDEFGHIJKLMNOPQRSTUVWXYZ
        // 01234567890123456789012345
        // A(0) +1 = B(0) -> K(10) -1 = J(9)
        // A(0) -1 = Z(0) -> J(9) +1 = K(10)
        Rotor1 rotor = new Rotor1("EKMFLGDQVZNTOWYHXUSPAIBRCJQ".toCharArray());
        Wiring wiring = new Wiring() {

            @Override
            public void forward(int pin) {
                assertEquals(10, pin);
            }

            @Override
            public void reverse(int pin) {
                assertEquals(10, pin);
            }
        };
        rotor.setOutput(wiring);
        rotor.setInput(wiring);
        rotor.setRing('B');
        rotor.forward(0);
        rotor.reverse(0);
    }

    @Test
    public void testEncipher() {

        Enigma machine = new Enigma();
        Machine config = new Machine(Machines.Type.ENIGMA, 1, 0, "IV II V | GMY | DN GR IS KC QX TM PV HY FW BJ |");
        machine.initialise(config);
        machine.setOffsets("DHO");
        System.out.println(machine);

        char[] original = "GXS".toCharArray();
        System.out.format("Original %s\n", Arrays.toString(original));
        char[] msgkey = machine.encode(original);
        System.out.format("Encoded  %s\n", Arrays.toString(msgkey));
        machine.reset();
        char[] verify = machine.encode(msgkey);
        System.out.format("Verify   %s\n", Arrays.toString(verify));

        assertEquals(Arrays.toString(original), Arrays.toString(verify));
        assertEquals("[R, L, P]", Arrays.toString(msgkey));

    }

    @Test
    public void testEnigma() {

        // U8K DE C 1806 = 49 = DHO GXS =
        // NQVLT YQFSE WWGJZ GQHVS EIXIM YKCNW IEBMB ATPPZ TDVCU PKAY-

        Enigma machine = new Enigma();
        Machine config = new Machine(Machines.Type.ENIGMA, 1, 0, "IV II V | GMY | DN GR IS KC QX TM PV HY FW BJ |");
        machine.initialise(config);
        machine.setOffsets("DHO");
        System.out.println(machine);

        // DETERMINE THE MESSAGE KEY ( GSX -> RLP )
        char[] original = "GXS".toCharArray();
        System.out.format("Original %s\n", Arrays.toString(original));
        char[] msgkey = machine.encode(original);
        System.out.format("Encoded  %s\n", Arrays.toString(msgkey));
        assertEquals("[R, L, P]", Arrays.toString(msgkey));

        // DETERMINE THE MESSAGE TEXT
        machine.setOffsets(msgkey);
        System.out.println(machine);
        char[] encoded = "NQVLT YQFSE WWGJZ GQHVS EIXIM YKCNW IEBMB ATPPZ TDVCU PKAY".toCharArray();
        char[] decoded = machine.encode(encoded);
        System.out.format("Decoded  %s\n", Arrays.toString(decoded));
        assertEquals(Arrays.toString("FLUGZ EUGFU EHRER ISTOF WYYXF UELLG RAFXF UELLG PAFXP OFOP".toCharArray()), Arrays.toString(decoded));
        // FLUGZEUGFUEHRERISTOFWYY X FUELLGRAF X FUELLGPAF X POFOP

    }

}
