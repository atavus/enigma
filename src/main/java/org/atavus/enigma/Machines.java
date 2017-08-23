package org.atavus.enigma;

public class Machines {

    public static enum Type {
        COMMERCIAL, ROCKET, SWISSK, ENIGMA, M3
    }

    public static enum Roman {
        I, II, III, IV, V, VI, VII, VIII, BETA, GAMMA
    }

    // https://en.wikipedia.org/wiki/Enigma_rotor_details

    private static final char[][][] rotor_wiring = { //
                    { //
                                    "DMTWSILRUYQNKFEJCAZBPGXOHV".toCharArray(), // Rotor # IC   , 1924 , Commercial Enigma A,B
                                    "HQZGPJTMOBLNCIFDYAWVEUSRKX".toCharArray(), // Rotor # IIC  , 1924 , Commercial Enigma A,B
                                    "UQNTLSZFMREHDPXKIBVYGJCWOA".toCharArray(), // Rotor # IIIC , 1924 , Commercial Enigma A,B
                    }, { //
                                    "JGDQOXUSCAMIFRVTPNEWKBLZYH".toCharArray(), // Rotor # I    , 1941 , German Railway (Rocket)
                                    "NTZPSFBOKMWRCJDIVLAEYUXHGQ".toCharArray(), // Rotor # II   , 1941 , German Railway (Rocket)
                                    "JVIUBHTCDYAKEQZPOSGXNRMWFL".toCharArray(), // Rotor # III  , 1941 , German Railway (Rocket)
                    }, { //
                                    "PEZUOHXSCVFMTBGLRINQJWAYDK".toCharArray(), // Rotor # I-K  , 1939 , Swiss K
                                    "ZOUESYDKFWPCIQXHMVBLGNJRAT".toCharArray(), // Rotor # II-K , 1939 , Swiss K
                                    "EHRVXGAOBQUSIMZFLYNWKTPDJC".toCharArray(), // Rotor # III-K, 1939 , Swiss K
                    }, { //
                                    "EKMFLGDQVZNTOWYHXUSPAIBRCJQ".toCharArray(), // Rotor # I    , 1930 , Enigma I
                                    "AJDKSIRUXBLHWTMCQGZNPYFVOEE".toCharArray(), // Rotor # II   , 1930 , Enigma I
                                    "BDFHJLCPRTXVZNYEIWGAKMUSQOV".toCharArray(), // Rotor # III  , 1930 , Enigma I
                                    "ESOVPZJAYQUIRHXLNFTGKDCMWBJ".toCharArray(), // Rotor # IV   , 1938 , M3 Army
                                    "VZBRGITYUPSDNHLXAWMJQOFECKZ".toCharArray(), // Rotor # V    , 1938 , M3 Army
                                    "JPGVOUMFYQBENHZRDKASXLICTWZM".toCharArray(), // Rotor # VI   , 1939 , M3 & M4 Naval (1942)
                                    "NZJHGRCXMYSWBOUFAIVLPEKQDTZM".toCharArray(), // Rotor # VII  , 1939 , M3 & M4 Naval (1942)
                                    "FKQHTLXOCBJSPDZRAMEWNIUYGVZM".toCharArray(), // Rotor # VIII , 1939 , M3 & M4 Naval (1942)
                                    "LEYJVCNIXWPBQMDRTAKZGFUHOS".toCharArray(), // Rotor # Beta , 1941 , M4 R2
                                    "FSOKANUERHMBTIYCWLQPZXVGJD".toCharArray(), // Rotor # Gamma, 1941 , M4 R2
                    }, { //
                                    "EKMFLGDQVZNTOWYHXUSPAIBRCJQ".toCharArray(), // Rotor # I    , 1930 , Enigma I
                                    "AJDKSIRUXBLHWTMCQGZNPYFVOEE".toCharArray(), // Rotor # II   , 1930 , Enigma I
                                    "BDFHJLCPRTXVZNYEIWGAKMUSQOV".toCharArray(), // Rotor # III  , 1930 , Enigma I
                                    "ESOVPZJAYQUIRHXLNFTGKDCMWBJ".toCharArray(), // Rotor # IV   , 1938 , M3 Army
                                    "VZBRGITYUPSDNHLXAWMJQOFECKZ".toCharArray(), // Rotor # V    , 1938 , M3 Army
                                    "JPGVOUMFYQBENHZRDKASXLICTWZM".toCharArray(), // Rotor # VI   , 1939 , M3 & M4 Naval (1942)
                                    "NZJHGRCXMYSWBOUFAIVLPEKQDTZM".toCharArray(), // Rotor # VII  , 1939 , M3 & M4 Naval (1942)
                                    "FKQHTLXOCBJSPDZRAMEWNIUYGVZM".toCharArray(), // Rotor # VIII , 1939 , M3 & M4 Naval (1942)
                    } };

    private static final char[][][] entry_wheel_wiring = { //
                    { //
                                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(), // No ETW on Commercial Enigma A,B
                    }, { //
                                    "QWERTZUIOASDFGHJKPYXCVBNML".toCharArray(), // Rotor # ETW  , 1941 , German Railway (Rocket)
                    }, { //
                                    "QWERTZUIOASDFGHJKPYXCVBNML".toCharArray(), // Rotor # ETW-K, 1939 , Swiss K
                    }, { //
                                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(), // Rotor # ETW Enigma I
                    }, { //
                                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(), // Rotor # ETW Enigma I
                    } };

    private static final char[][][] reflector_wiring = { //
                    { //
                                    "YRUHQSLDPXNGOKMIEBFZCWVJAT".toCharArray(), // Rotor # Reflector B
                    }, { //
                                    "QYHOGNECVPUZTFDJAXWMKISRBL".toCharArray(), // Rotor # UKW  , 1941 , German Railway (Rocket)
                    }, { //
                                    "IMETCGFRAYSQBZXWLHKDVUPOJN".toCharArray(), // Rotor # UKW-K, 1939 , Swiss K
                    }, { //
                                    "EJMZALYXVBWFCRQUONTSPIKHGD".toCharArray(), // Rotor # Reflector A
                                    "YRUHQSLDPXNGOKMIEBFZCWVJAT".toCharArray(), // Rotor # Reflector B
                                    "FVPJIAOYEDRZXWGCTKUQSBNMHL".toCharArray(), // Rotor # Reflector C
                                    "ENKQAUYWJICOPBLMDXZVFTHRGS".toCharArray(), // Rotor # Reflector B Thin 1940 M4 R1 (M3 + Thin)
                                    "RDOBJNTKVEHMLFCWZAXGYIPSUQ".toCharArray() // Rotor # Reflector C Thin 1940 M4 R1 (M3 + Thin)
                    }, { //
                                    "YRUHQSLDPXNGOKMIEBFZCWVJAT".toCharArray(), // Rotor # Reflector B
                                    "FVPJIAOYEDRZXWGCTKUQSBNMHL".toCharArray(), // Rotor # Reflector C
                                    "ENKQAUYWJICOPBLMDXZVFTHRGS".toCharArray(), // Rotor # Reflector B Thin 1940 M4 R1 (M3 + Thin)
                                    "RDOBJNTKVEHMLFCWZAXGYIPSUQ".toCharArray() // Rotor # Reflector C Thin 1940 M4 R1 (M3 + Thin)
                    } };

    private static final Machines machines = new Machines();

    public static final Machines getMachines() {
        return machines;
    }

    private Rotor[][] rotors = new Rotor[rotor_wiring.length][];
    private Reflector[][] reflectors = new Reflector[reflector_wiring.length][];
    private ETW[][] etws = new ETW[entry_wheel_wiring.length][];

    private ThreadLocal<Rotor[][]> thread_rotors = ThreadLocal.withInitial(() -> new Rotor[rotor_wiring.length][]);
    private ThreadLocal<Reflector[][]> thread_reflectors = ThreadLocal.withInitial(() -> new Reflector[reflector_wiring.length][]);
    private ThreadLocal<ETW[][]> thread_etws = ThreadLocal.withInitial(() -> new ETW[entry_wheel_wiring.length][]);

    public Machines() {

        // create the rotors
        for (int mc = 0; mc < rotor_wiring.length; mc++) {
            rotors[mc] = new Rotor[rotor_wiring[mc].length];
            for (int rotor = 0; rotor < rotor_wiring[mc].length; rotor++) {
                rotors[mc][rotor] = new Rotor2(rotor_wiring[mc][rotor]);
            }
        }

        // create the reflectors
        for (int mc = 0; mc < reflector_wiring.length; mc++) {
            reflectors[mc] = new Reflector[reflector_wiring[mc].length];
            for (int reflector = 0; reflector < reflector_wiring[mc].length; reflector++) {
                reflectors[mc][reflector] = new Reflector(reflector_wiring[mc][reflector]);
            }
        }

        // create the entry wheels
        for (int mc = 0; mc < entry_wheel_wiring.length; mc++) {
            etws[mc] = new ETW[entry_wheel_wiring[mc].length];
            for (int etw = 0; etw < entry_wheel_wiring[mc].length; etw++) {
                etws[mc][etw] = new ETW(entry_wheel_wiring[mc][etw]);
            }
        }

    }

    public int countReflectors(Type machine) {
        return reflectors[machine.ordinal()].length;
    }

    public Reflector getReflector(Type machine, int reflector_number) {
        Reflector[][] ref = thread_reflectors.get();
        if (ref[machine.ordinal()] == null)
            ref[machine.ordinal()] = new Reflector[reflector_wiring[machine.ordinal()].length];
        if (ref[machine.ordinal()][reflector_number] == null)
            ref[machine.ordinal()][reflector_number] = reflectors[machine.ordinal()][reflector_number].clone();
        return ref[machine.ordinal()][reflector_number];
    }

    public int countRotors(Type machine) {
        return rotors[machine.ordinal()].length;
    }

    public Rotor getRotor(Type machine, int rotor_number) {
        Rotor[][] ref = thread_rotors.get();
        if (ref[machine.ordinal()] == null)
            ref[machine.ordinal()] = new Rotor[rotor_wiring[machine.ordinal()].length];
        if (ref[machine.ordinal()][rotor_number] == null)
            ref[machine.ordinal()][rotor_number] = rotors[machine.ordinal()][rotor_number].clone();
        return ref[machine.ordinal()][rotor_number];
    }

    public int countETWs(Type machine) {
        return etws[machine.ordinal()].length;
    }

    public ETW getETW(Type machine, int etw_number) {
        ETW[][] ref = thread_etws.get();
        if (ref[machine.ordinal()] == null)
            ref[machine.ordinal()] = new ETW[entry_wheel_wiring[machine.ordinal()].length];
        if (ref[machine.ordinal()][etw_number] == null)
            ref[machine.ordinal()][etw_number] = etws[machine.ordinal()][etw_number].clone();
        return ref[machine.ordinal()][etw_number];
    }

}
