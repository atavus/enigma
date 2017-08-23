package org.atavus.enigma.test;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.atavus.enigma.Enigma;
import org.atavus.enigma.Machine;
import org.atavus.enigma.Machines;
import org.atavus.enigma.Machines.Roman;
import org.atavus.enigma.Machines.Type;

public class AllM3Codes extends Thread {

    public static void main(String[] args) throws Exception {
        new AllM3Codes().run();
    }

    private static class Pair {
        private final char[] pair;

        public Pair(char... pairs) {
            pair = Arrays.copyOf(pairs, 2);
        }

        public char left() {
            return pair[0];
        }

        public char right() {
            return pair[1];
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Pair))
                return false;
            Pair p = (Pair) o;
            return (p.pair[0] == pair[0] && p.pair[1] == pair[1]);
        }

        @Override
        public int hashCode() {
            return pair[0] + pair[1];
        }

        @Override
        public String toString() {
            return new String(pair);
        }

    }

    private static class Pairs implements Iterable<Pair> {
        private Set<Pair> pairs = new HashSet<>();

        public void add(Pair pair) {
            pairs.add(pair);
        }

        @Override
        public Iterator<Pair> iterator() {
            return pairs.iterator();
        }

    }

    private static class Loop implements Cloneable {
        private List<Pair> pairs = new LinkedList<>();
        private StringBuilder out = new StringBuilder();
        private char right;

        @Override
        public Loop clone() {
            Loop clone = new Loop();
            for (Pair pair : pairs) {
                clone.add(pair);
            }
            return clone;
        }

        public boolean add(Pair pair) {
            this.pairs.add(pair);
            out.append(pair.left());
            right = pair.right();
            return (left() == right());
        }

        public char left() {
            return out.charAt(0);
        }

        public char right() {
            return right;
        }

        @Override
        public String toString() {
            return out.toString();
        }

        @Override
        public int hashCode() {
            return out.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Loop))
                return false;
            return (o.toString().equals(toString()));
        }

    }

    private static class Loops implements Iterable<Loop> {
        private Set<Loop> loops = new HashSet<>();

        public void add(Loop loop) {
            loops.add(loop);
        }

        @Override
        public Iterator<Loop> iterator() {
            return loops.iterator();
        }

        @Override
        public String toString() {
            StringBuilder out = new StringBuilder();
            for (Loop loop : loops) {
                if (out.length() > 0)
                    out.append(" ");
                out.append(String.format("%02d", loop.toString().length()));
            }
            return out.toString();
        }

    }

    @Override
    public void run() {
        // 4 reflectors
        // 8x7x6 rotors
        // 26x26x26 ring offsets
        // = 415,182,802,944 combinations
        try {
            final char[] input = new char[6];
            final char[] output = new char[6];
            final Enigma machine = new Enigma();
            machine.debug(false);
            final Type type = Type.M3;
            final Machine config = new Machine(type);
            config.etw_number(0);
            final PrintStream outs[] = new PrintStream[3];
            for (int reflector_number = 0; reflector_number < Machines.getMachines().countReflectors(type); reflector_number++) {
                config.reflector_number(reflector_number);
                for (int ofs = 0; ofs < 3; ofs++) {
                    outs[ofs] = new PrintStream(new FileOutputStream(String.format("m3.%c.%d.codes.txt", reflector_number + 'a', ofs)));
                }
                for (int rotor1_number = 0; rotor1_number < Machines.getMachines().countRotors(type); rotor1_number++) {
                    config.rotor1_number(rotor1_number);
                    config.ring1_number('A');
                    for (int rotor2_number = 0; rotor2_number < Machines.getMachines().countRotors(type); rotor2_number++) {
                        if (rotor2_number == rotor1_number)
                            continue;
                        config.rotor2_number(rotor2_number);
                        config.ring2_number('A');
                        for (int rotor3_number = 0; rotor3_number < Machines.getMachines().countRotors(type); rotor3_number++) {
                            if (rotor3_number == rotor1_number || rotor3_number == rotor2_number)
                                continue;
                            config.rotor3_number(rotor3_number);
                            config.ring3_number('A');
                            for (int ring1_offset = 1; ring1_offset <= 26; ring1_offset++) {
                                config.ring1_offset(ring1_offset);
                                for (int ring2_offset = 1; ring2_offset <= 26; ring2_offset++) {
                                    config.ring2_offset(ring2_offset);
                                    for (int ring3_offset = 1; ring3_offset <= 26; ring3_offset++) {
                                        config.ring3_offset(ring3_offset);
                                        Pairs[] pairs = new Pairs[3];
                                        pairs[0] = new Pairs();
                                        pairs[1] = new Pairs();
                                        pairs[2] = new Pairs();
                                        for (char letter1 = 'A'; letter1 <= 'Z'; letter1++) {
                                            input[0] = letter1;
                                            input[3] = letter1;
                                            for (char letter2 = 'A'; letter2 <= 'Z'; letter2++) {
                                                input[1] = letter2;
                                                input[4] = letter2;
                                                for (char letter3 = 'A'; letter3 <= 'Z'; letter3++) {
                                                    input[2] = letter3;
                                                    input[5] = letter3;
                                                    machine.initialise(config);
                                                    machine.encode(input, output);
                                                    for (int ofs = 0; ofs < 3; ofs++) {
                                                        pairs[ofs].add(new Pair(output[ofs], output[ofs + 3]));
                                                    }
                                                }
                                            }
                                        }
                                        // calculate loops
                                        for (int ofs = 0; ofs < 3; ofs++) {
                                            Loops loops = calculateLoops(pairs[ofs]);
                                            outs[ofs].format("| %c | %s %s %s | %c%c%c | %s |\n", reflector_number + 'A',
                                                            Roman.values()[rotor1_number], Roman.values()[rotor2_number],
                                                            Roman.values()[rotor3_number], ring1_offset + 'A' - 1, ring2_offset + 'A' - 1,
                                                            ring3_offset + 'A' - 1, loops);
                                            outs[ofs].flush();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                for (int ofs = 0; ofs < 3; ofs++) {
                    outs[ofs].close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.format("Thread %s terminated.", Thread.currentThread().getName());
        }
    }

    private Loops calculateLoops(Pairs pairs) {
        Loops loops = new Loops();
        for (Pair pair : pairs) {
            Loop loop = new Loop();
            if (loop.add(pair)) {
                loops.add(loop);
            } else {
                calculateLoops(loops, pairs, loop);
            }
        }
        return loops;
    }

    private void calculateLoops(Loops loops, Pairs pairs, Loop loop) {
        for (Pair pair : pairs) {
            if (loop.right() == pair.left()) {
                Loop oloop = loop.clone();
                if (oloop.add(pair)) {
                    loops.add(oloop);
                } else {
                    calculateLoops(loops, pairs, oloop);
                }
            }
        }
    }

}
