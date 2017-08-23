package org.atavus.enigma;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EnigmaRotorSpeedTest implements Wiring {

    private int testIndex;
    private int testRing;
    private int testOffset;
    private Rotor1 rotor;
    private Rotor2 rotor2;
    private Rotor3 rotor3;
    private String forward = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
    private long expectForward;
    private long expectReverse;
    private long expectForwards[][][] = new long[26][26][26];
    private long expectReverses[][][] = new long[26][26][26];

    public EnigmaRotorSpeedTest() {
        rotor = new Rotor1("EKMFLGDQVZNTOWYHXUSPAIBRCJQ".toCharArray());
        rotor.setInput(this);
        rotor.setOutput(this);
        rotor2 = new Rotor2("EKMFLGDQVZNTOWYHXUSPAIBRCJQ".toCharArray());
        rotor2.setInput(this);
        rotor2.setOutput(this);
        rotor3 = new Rotor3("EKMFLGDQVZNTOWYHXUSPAIBRCJQ".toCharArray());
        rotor3.setInput(this);
        rotor3.setOutput(this);
        for (testRing = 0; testRing < 26; testRing++) {
            for (testOffset = 0; testOffset < 26; testOffset++) {
                for (testIndex = 0; testIndex < 26; testIndex++) {
                    expectForwards[testRing][testOffset][testIndex] = (26 + forward.charAt((26 + testIndex - testRing + testOffset) % 26)
                                    - 'A' + testRing - testOffset) % 26;
                    expectReverses[testRing][testOffset][testIndex] = (26
                                    + forward.indexOf((26 + testIndex - testRing + testOffset) % 26 + 'A') + testRing - testOffset) % 26;
                }
            }
        }
    }

    @Test
    public void testRingOffsetWiringForwardRotor1() {
        for (int repeat = 0; repeat < 100000; repeat++) {
            for (testRing = 0; testRing < 26; testRing++) {
                rotor.setRing(testRing + 1);
                for (testOffset = 0; testOffset < 26; testOffset++) {
                    rotor.setOffset(testOffset + 1);
                    for (testIndex = 0; testIndex < 26; testIndex++) {
                        expectForward = expectForwards[testRing][testOffset][testIndex];
                        rotor.forward(testIndex);
                    }
                }
            }
        }
    }

    @Test
    public void testRingOffsetWiringReverseRotor1() {
        for (int repeat = 0; repeat < 100000; repeat++) {
            for (testRing = 0; testRing < 26; testRing++) {
                rotor.setRing(testRing + 1);
                for (testOffset = 0; testOffset < 26; testOffset++) {
                    rotor.setOffset(testOffset + 1);
                    for (testIndex = 0; testIndex < 26; testIndex++) {
                        expectReverse = expectReverses[testRing][testOffset][testIndex];
                        rotor.reverse(testIndex);
                    }
                }
            }
        }
    }

    @Test
    public void testRingOffsetWiringForwardRotor2() {
        for (int repeat = 0; repeat < 100000; repeat++) {
            for (testRing = 0; testRing < 26; testRing++) {
                rotor2.setRing(testRing + 1);
                for (testOffset = 0; testOffset < 26; testOffset++) {
                    rotor2.setOffset(testOffset + 1);
                    for (testIndex = 0; testIndex < 26; testIndex++) {
                        expectForward = expectForwards[testRing][testOffset][testIndex];
                        rotor2.forward(testIndex);
                    }
                }
            }
        }
    }

    @Test
    public void testRingOffsetWiringReverseRotor2() {
        for (int repeat = 0; repeat < 100000; repeat++) {
            for (testRing = 0; testRing < 26; testRing++) {
                rotor2.setRing(testRing + 1);
                for (testOffset = 0; testOffset < 26; testOffset++) {
                    rotor2.setOffset(testOffset + 1);
                    for (testIndex = 0; testIndex < 26; testIndex++) {
                        expectReverse = expectReverses[testRing][testOffset][testIndex];
                        rotor2.reverse(testIndex);
                    }
                }
            }
        }
    }

    @Test
    public void testRingOffsetWiringForwardRotor3() {
        for (int repeat = 0; repeat < 100000; repeat++) {
            for (testRing = 0; testRing < 26; testRing++) {
                rotor3.setRing(testRing + 1);
                for (testOffset = 0; testOffset < 26; testOffset++) {
                    rotor3.setOffset(testOffset + 1);
                    for (testIndex = 0; testIndex < 26; testIndex++) {
                        expectForward = expectForwards[testRing][testOffset][testIndex];
                        rotor3.forward(testIndex);
                    }
                }
            }
        }
    }

    @Test
    public void testRingOffsetWiringReverseRotor3() {
        for (int repeat = 0; repeat < 100000; repeat++) {
            for (testRing = 0; testRing < 26; testRing++) {
                rotor3.setRing(testRing + 1);
                for (testOffset = 0; testOffset < 26; testOffset++) {
                    rotor3.setOffset(testOffset + 1);
                    for (testIndex = 0; testIndex < 26; testIndex++) {
                        expectReverse = expectReverses[testRing][testOffset][testIndex];
                        rotor3.reverse(testIndex);
                    }
                }
            }
        }
    }

    @Override
    public void forward(int pin) {
        assertEquals(expectForward, pin);
    }

    @Override
    public void reverse(int pin) {
        assertEquals(expectReverse, pin);
    }

}
