package sisc.instructions;

import static sisc.RegFile.*;

import sisc.PC;

public class Branching {
	private static final int E_MASK = 0b100000000;
	public static void handle(short s) {
		int address = (s >> 9) & 0b111;
		if ((s & E_MASK) == 0) { // e == 0
			// BZ
			if (getReg(address) == 0) PC.jump((byte)s);
		} else {
			// BNZ
			if (getReg(address) != 0) PC.jump((byte)s);
		}
	}
}
