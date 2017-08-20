# Enigma machine
Software implementation of the Enigma machine
Copyright (c) 2017 David Johnston
All rights reserved

| Class        | Purpose                                                                                     |
| ------------ | ------------------------------------------------------------------------------------------- |
| Enigma       | Enigma machine housing to perform encoding and decoding                                     |
| ETW          | Entry wheel (Eintrittswalze)                                                                |
| Keyboard     | Keyboard for encoding a single letter                                                       |
| Machine      | Enigma machine configuration and wiring                                                     |
| Machines     | Set of the various enigma machine settings                                                  |
| Reflector    | Reflector wiring                                                                            |
| Rotor        | Rotor wiring                                                                                |
| Steckerboard | Plug board (Steckerbrett)                                                                   |
| FindCode     | Given a thread count and an input string, find likely english decoding using random testing |
| EnigmaTest   | Simple check of the logic using the Enigma movie encoding as an example                     |

## Wiring configuration

Keyboard -> Steckerboard -> ETW -> Rotor 3 -> Rotor 2 -> Rotor 1 -> Reflector -> Rotor 1 -> Rotor 2 -> Rotor 3 -> ETW -> Steckerboard -> Bulb

Each rotor has 26 pins on each side. These are assigned to letters: 1=A, 2=B, 3=C, etc.
The internal wiring connects a pin on one side to a different pin on the other side.
A wire is never between the same pin on each side.
Therefore a letter never translates to itself.
The wire runs in both directions.
e.g. if the wire runs between A and F then the charge travels from A to F, and from F to A.

The wiring can be internally translated so that the wire connects to different external pins.
e.g. if the wire was running between A and F then adjusting the ring to B results in the wire running between Z and E.

