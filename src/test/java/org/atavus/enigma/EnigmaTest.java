package org.atavus.enigma;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class EnigmaTest {

    @Test
    public void testEncipher() {

        Enigma machine = new Enigma();
        Configuration config = new Configuration(Machines.Type.ENIGMA, 1, 0, "IV II V | GMY | DN GR IS KC QX TM PV HY FW BJ |");
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
        Configuration config = new Configuration(Machines.Type.ENIGMA, 1, 0, "IV II V | GMY | DN GR IS KC QX TM PV HY FW BJ |");
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
