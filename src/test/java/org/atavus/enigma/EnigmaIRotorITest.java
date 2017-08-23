package org.atavus.enigma;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EnigmaIRotorITest implements Wiring {

    private int testIndex;
    private int testRing;
    private int testOffset;
    private Rotor1 rotor;
    private String forward = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
    private long expectForward;
    private long expectReverse;

    public EnigmaIRotorITest() {
        rotor = new Rotor1("EKMFLGDQVZNTOWYHXUSPAIBRCJQ".toCharArray());
        rotor.setInput(this);
        rotor.setOutput(this);
    }

    @Test
    public void testWiringForward() {
        rotor.setRing(1);
        for (testIndex = 0; testIndex < 26; testIndex++) {
            expectForward = forward.charAt(testIndex) - 'A';
            rotor.forward(testIndex);
        }
    }

    @Test
    public void testRingWiringForward() {
        for (testRing = 0; testRing < 26; testRing++) {
            rotor.setRing(testRing + 1);
            for (testIndex = 0; testIndex < 26; testIndex++) {
                expectForward = (forward.charAt((26 + testIndex - testRing) % 26) - 'A' + testRing) % 26;
                rotor.forward(testIndex);
            }
        }
    }

    @Test
    public void testRingOffsetWiringForward() {
        for (testRing = 0; testRing < 26; testRing++) {
            rotor.setRing(testRing + 1);
            for (testOffset = 0; testOffset < 26; testOffset++) {
                rotor.setOffset(testOffset + 1);
                for (testIndex = 0; testIndex < 26; testIndex++) {
                    expectForward = (26 + forward.charAt((26 + testIndex - testRing + testOffset) % 26) - 'A' + testRing - testOffset) % 26;
                    rotor.forward(testIndex);
                }
            }
        }
    }

    @Test
    public void testWiringReverse() {
        rotor.setRing(1);
        for (testIndex = 0; testIndex < 26; testIndex++) {
            expectReverse = forward.indexOf(testIndex + 'A');
            rotor.reverse(testIndex);
        }
    }

    @Test
    public void testRingWiringReverse() {
        for (testRing = 0; testRing < 26; testRing++) {
            rotor.setRing(testRing + 1);
            for (testIndex = 0; testIndex < 26; testIndex++) {
                expectReverse = (forward.indexOf((26 + testIndex - testRing) % 26 + 'A') + testRing) % 26;
                rotor.reverse(testIndex);
            }
        }
    }

    @Test
    public void testRingOffsetWiringReverse() {
        for (testRing = 0; testRing < 26; testRing++) {
            rotor.setRing(testRing + 1);
            for (testOffset = 0; testOffset < 26; testOffset++) {
                rotor.setOffset(testOffset + 1);
                for (testIndex = 0; testIndex < 26; testIndex++) {
                    expectReverse = (26 + forward.indexOf((26 + testIndex - testRing + testOffset) % 26 + 'A') + testRing - testOffset)
                                    % 26;
                    rotor.reverse(testIndex);
                }
            }
        }
    }

    @Override
    public void forward(int pin) {
        assertEquals(String.format("Forward input %d, ring %d", testIndex, testRing), expectForward, pin);
    }

    @Override
    public void reverse(int pin) {
        assertEquals(String.format("Reverse input %d, ring %d", testIndex, testRing), expectReverse, pin);
    }

}
