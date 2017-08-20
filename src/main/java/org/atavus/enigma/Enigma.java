package org.atavus.enigma;

public class Enigma {

    private Machine config;

    private boolean debug;

    public void debug(boolean debug) {
        this.debug = debug;
    }

    public void initialise(final Machine settings) {
        config = settings;
        config.build();
    }

    public void reset() {
        config.reset();
    }

    public char[] encode(char[] original) {
        char[] out = new char[original.length];
        encode(original, out);
        return out;
    }

    public void encode(char[] original, char[] out) {
        for (int ofs = 0; ofs < original.length; ofs++) {
            if (original[ofs] == ' ') {
                out[ofs] = ' ';
            } else {
                if (config.rotor3().advance()) {
                    if (config.rotor2().advance()) {
                        config.rotor1().advance();
                    }
                }
                out[ofs] = config.kbd().encode(original[ofs]);
            }
            if (debug)
                System.out.format("%s %c -> %c\n", config.getOffsets(), original[ofs], out[ofs]);
        }
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(String.format("%s\n", config));
        out.append("  A B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n");
        out.append(String.format("%s\n", config.rotor3()));
        out.append(String.format("%s\n", config.rotor2()));
        out.append(String.format("%s\n", config.rotor1()));
        out.append(String.format("%s\n", config.reflector()));
        out.append(String.format("%s\n", config.board()));
        return out.toString();
    }

    public void setOffsets(String offsets) {
        config.setOffsets(offsets);
    }

    public void setOffsets(char... offsets) {
        config.setOffsets(offsets);
    }

}
