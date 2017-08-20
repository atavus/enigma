package org.atavus.enigma.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.atavus.enigma.Enigma;
import org.atavus.enigma.Machine;
import org.atavus.enigma.Machines.Type;

public class FindM3Code extends Thread {

    private char[] input;

    private static long instances;
    private static long from = System.currentTimeMillis();

    private static PrintStream outs;

    static {
        try {
            outs = new PrintStream(new FileOutputStream("m3.output.txt"));
        } catch (IOException e) {
            outs = System.out;
        }
    }

    public static void main(String[] args) throws Exception {
        outs.format("Threads %s\n", args[0]);
        outs.format("Input %s\n", args[1]);
        outs.println();
        outs.flush();
        Thread[] threads = new Thread[Integer.parseInt(args[0])];
        for (int t = 0; t < threads.length; t++) {
            threads[t] = new FindM3Code(args[1]);
            threads[t].start();
        }
    }

    public FindM3Code(String input) {
        this.input = input.toCharArray();
    }

    private synchronized static void nextInstance() {
        instances++;
        if ((instances % 100000000) == 0) {
            System.out.format("%s %s Checked %s instances\n", new Date(System.currentTimeMillis() - from), Thread.currentThread().getName(),
                            NumberFormat.getInstance().format(instances));
        }
    }

    @Override
    public void run() {
        try {
            Random random = new Random();
            char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            char[] output = new char[input.length];
            Enigma machine = new Enigma();
            machine.debug(false);
            Machine config = new Machine(Type.M3);
            while (true) {
                config.randomise(random);
                machine.initialise(config);
                machine.encode(input, output);
                // calculate letter frequency distribution
                int[] dist = new int[26];
                for (int ofs = 0; ofs < output.length; ofs++) {
                    if (output[ofs] != ' ') {
                        dist[output[ofs] - 'A']++;
                    }
                }
                // order letters by frequency, most frequent first
                Map<Integer, List<Character>> rank = new TreeMap<>(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                });
                for (int ofs = 0; ofs < dist.length; ofs++) {
                    List<Character> list = rank.computeIfAbsent(dist[ofs], (k) -> new LinkedList<>());
                    list.add((char) (ofs + 'A'));
                }
                char[] ord = new char[26];
                int ofs = 0;
                for (Map.Entry<Integer, List<Character>> entry : rank.entrySet()) {
                    for (Character c : entry.getValue()) {
                        ord[ofs++] = c;
                    }
                }
                String order = new String(ord);
                // check if the letter distribution makes sense
                if (order.indexOf('A') <= 6 && order.indexOf('E') <= 6 && order.indexOf('I') <= 6 && order.indexOf('O') <= 6
                                && order.indexOf('T') <= 12 && order.indexOf('N') <= 12 && order.indexOf('S') <= 12
                                && order.indexOf('R') <= 12 && order.indexOf('Z') >= 20 && order.indexOf('J') >= 20
                                && order.indexOf('X') >= 20 && order.indexOf('Q') >= 20 && order.indexOf('V') >= 18
                                && order.indexOf('K') >= 18 && order.indexOf('W') >= 13 && order.indexOf('M') >= 13) {
                    String inwords = new String(input);
                    String outwords = new String(output);
                    report(System.out, inwords, outwords, order, alphabet, dist, config);
                    report(outs, inwords, outwords, order, alphabet, dist, config);
                }
                nextInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.format("Thread %s terminated.", Thread.currentThread().getName());
        }
    }

    private static synchronized void report(PrintStream out, String inwords, String outwords, String order, char[] alphabet, int[] dist,
                    Machine config) {
        out.format("%s\n", config);
        out.format("%s\n", outwords);
        out.println();
        out.flush();
    }

}
