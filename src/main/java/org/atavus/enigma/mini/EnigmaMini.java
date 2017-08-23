package org.atavus.enigma.mini;

import java.util.Random;

public class EnigmaMini {

    public static enum Roman {
        I, II, III, IV, V, VI, VII, VIII
    }

    public static enum Reflector {
        B, C, ThinB, ThinC
    }

    private static char[][] rotor_wiring = { //
                    "EKMFLGDQVZNTOWYHXUSPAIBRCJQ".toCharArray(), // Rotor # I    , 1930 , Enigma I
                    "AJDKSIRUXBLHWTMCQGZNPYFVOEE".toCharArray(), // Rotor # II   , 1930 , Enigma I
                    "BDFHJLCPRTXVZNYEIWGAKMUSQOV".toCharArray(), // Rotor # III  , 1930 , Enigma I
                    "ESOVPZJAYQUIRHXLNFTGKDCMWBJ".toCharArray(), // Rotor # IV   , 1938 , M3 Army
                    "VZBRGITYUPSDNHLXAWMJQOFECKZ".toCharArray(), // Rotor # V    , 1938 , M3 Army
                    "JPGVOUMFYQBENHZRDKASXLICTWZM".toCharArray(), // Rotor # VI   , 1939 , M3 & M4 Naval (1942)
                    "NZJHGRCXMYSWBOUFAIVLPEKQDTZM".toCharArray(), // Rotor # VII  , 1939 , M3 & M4 Naval (1942)
                    "FKQHTLXOCBJSPDZRAMEWNIUYGVZM".toCharArray() // Rotor # VIII , 1939 , M3 & M4 Naval (1942)
    };

    private static char[][] reflector_wiring = { //
                    "YRUHQSLDPXNGOKMIEBFZCWVJAT".toCharArray(), // Rotor # Reflector B
                    "FVPJIAOYEDRZXWGCTKUQSBNMHL".toCharArray(), // Rotor # Reflector C
                    "ENKQAUYWJICOPBLMDXZVFTHRGS".toCharArray(), // Rotor # Reflector B Thin 1940 M4 R1 (M3 + Thin)
                    "RDOBJNTKVEHMLFCWZAXGYIPSUQ".toCharArray() // Rotor # Reflector C Thin 1940 M4 R1 (M3 + Thin)
    };

    private class Rotor {
        private int[] forwardmap = new int[17576];
        private int[] reversemap = new int[17576];
        private int notch1 = 26;
        private int notch2 = 26;
        private int ring;
        private int offset;

        public boolean advance() {
            boolean turnover = (offset == notch1 || offset == notch2);
            offset = (offset + 1) % 26;
            return turnover;
        }

    }

    private Rotor[] rotors = new Rotor[8];

    private Rotor rotor1 = new Rotor();
    private Rotor rotor2 = new Rotor();
    private Rotor rotor3 = new Rotor();

    private int[] reflector = new int[26];
    private int[] steckerboard = new int[26];

    private Random random = new Random();

    public EnigmaMini() {
        initialiseRotors();
    }

    public void initialiseRandom() {
        randomSteckerboard(10);
        randomReflector();
        randomRotors();
    }

    private void advance() {
        if (rotor3.advance()) {
            if (rotor2.advance()) {
                rotor1.advance();
            }
        }
    }

    public void encode(char[] original, char[] out) {
        for (int ofs = 0; ofs < original.length; ofs++) {
            if (original[ofs] == ' ') {
                out[ofs] = ' ';
            } else {
                advance();
                out[ofs] = (char) (encode(original[ofs] - 'A') + 'A');
            }
        }
    }

    private int encode(int pin) {
        int r1 = rotor1.ring * 26 + rotor1.offset * 676;
        int r2 = rotor2.ring * 26 + rotor2.offset * 676;
        int r3 = rotor3.ring * 26 + rotor3.offset * 676;
        pin = steckerboard[pin];
        pin = rotor3.forwardmap[pin + r3];
        pin = rotor2.forwardmap[pin + r2];
        pin = rotor1.forwardmap[pin + r1];
        pin = reflector[pin];
        pin = rotor1.reversemap[pin + r1];
        pin = rotor2.reversemap[pin + r2];
        pin = rotor3.reversemap[pin + r3];
        pin = steckerboard[pin];
        return pin;
    }

    public void randomSteckerboard(final int pairs) {
        for (int pin = 0; pin < 26; pin++) {
            steckerboard[pin] = -1;
        }
        int sets = 0;
        while (sets < pairs) {
            int pin1 = random.nextInt(26);
            int pin2 = random.nextInt(26);
            if (pin1 != pin2 && steckerboard[pin1] == -1 && steckerboard[pin2] == -1) {
                steckerboard[pin1] = pin2;
                steckerboard[pin2] = pin1;
                sets++;
            }
        }
        for (int pin = 0; pin < 26; pin++) {
            if (steckerboard[pin] == -1)
                steckerboard[pin] = pin;
        }
    }

    public void randomReflector() {
        int reflect = random.nextInt(4);
        for (int pin = 0; pin < 26; pin++) {
            reflector[pin] = reflector_wiring[reflect][pin] - 'A';
        }
    }

    public void randomRotors() {
        int rotor1_number = random.nextInt(8);
        int rotor2_number = random.nextInt(8);
        int rotor3_number = random.nextInt(8);
        while (rotor2_number == rotor1_number)
            rotor2_number = random.nextInt(8);
        while (rotor3_number == rotor1_number || rotor3_number == rotor2_number)
            rotor3_number = random.nextInt(8);
        rotor1 = rotors[rotor1_number];
        rotor2 = rotors[rotor2_number];
        rotor3 = rotors[rotor3_number];
    }

    public void setupSteckerboard() {
        for (int pin = 0; pin < 26; pin++) {
            steckerboard[pin] = pin;
        }
    }

    public void setupSteckerboard(String... strings) {
        for (int pin = 0; pin < 26; pin++) {
            steckerboard[pin] = pin;
        }
        for (String string : strings) {
            steckerboard[string.charAt(0) - 'A'] = string.charAt(1) - 'A';
            steckerboard[string.charAt(1) - 'A'] = string.charAt(0) - 'A';
        }
    }

    public void setupReflector(final Reflector reflect) {
        for (int pin = 0; pin < 26; pin++) {
            reflector[pin] = reflector_wiring[reflect.ordinal()][pin] - 'A';
        }
    }

    public void setupRotor1(final Roman rotor) {
        rotor1 = rotors[rotor.ordinal()];
    }

    public void setupRotor2(final Roman rotor) {
        rotor2 = rotors[rotor.ordinal()];
    }

    public void setupRotor3(final Roman rotor) {
        rotor3 = rotors[rotor.ordinal()];
    }

    public void setRotor1Ring(final int ring) {
        rotor1.ring = ((ring % 26) + 26) % 26;
    }

    public void setRotor2Ring(final int ring) {
        rotor2.ring = ((ring % 26) + 26) % 26;
    }

    public void setRotor3Ring(final int ring) {
        rotor3.ring = ((ring % 26) + 26) % 26;
    }

    public void setRotor1Offset(final int offset) {
        rotor1.offset = ((offset % 26) + 26) % 26;
    }

    public void setRotor2Offset(final int offset) {
        rotor2.offset = ((offset % 26) + 26) % 26;
    }

    public void setRotor3Offset(final int offset) {
        rotor3.offset = ((offset % 26) + 26) % 26;
    }

    public void initialiseRotors() {
        for (int rotor = 0; rotor < 8; rotor++) {
            rotors[rotor] = new Rotor();
            initialiseRotor(rotor_wiring[rotor], rotors[rotor]);
        }
    }

    private void initialiseRotor(final char[] wiring, final Rotor rotor) {
        int[] forward = new int[26];
        int[] reverse = new int[26];
        for (int pin = 0; pin < 26; pin++) {
            forward[pin] = wiring[pin] - 'A';
            reverse[wiring[pin] - 'A'] = pin;
        }
        if (wiring.length > 26) {
            rotor.notch1 = wiring[26] - 'A';
        }
        if (wiring.length > 27) {
            rotor.notch2 = wiring[27] - 'A';
        }
        for (int pin = 0; pin < 26; pin++) {
            for (int ring = 0; ring < 26; ring++) {
                for (int ofs = 0; ofs < 26; ofs++) {
                    rotor.forwardmap[pin + ring * 26 + ofs * 676] = (forward[(pin + 52 - ring + ofs) % 26] + 52 - ofs + ring) % 26;
                    rotor.reversemap[pin + ring * 26 + ofs * 676] = (reverse[(pin + 52 - ring + ofs) % 26] + 52 - ofs + ring) % 26;
                }
            }
        }
    }

}
