package org.atavus.enigma.test;

import java.io.File;
import java.util.Scanner;

public class FindLoops {

    public static void main(String[] args) throws Exception {
        try (Scanner in = new Scanner(new File("crib.txt"));) {
            while (in.hasNextLine()) {
                char[] key1 = in.next().toCharArray();
                char[] key2 = in.next().toCharArray();
                char[][] pair = new char[3][2];
                for (int ofs = 0; ofs < 3; ofs++) {
                    pair[ofs][0] = key1[ofs];
                    pair[ofs][1] = key2[ofs];
                }
                System.out.format("%c%c %c%c %c%c\n", pair[0][0], pair[0][1], pair[1][0], pair[1][1], pair[2][0], pair[2][1]);
            }
        }
    }

}
