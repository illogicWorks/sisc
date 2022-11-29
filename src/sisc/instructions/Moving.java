package sisc.instructions;

import static sisc.RegFile.*;

public class Moving {
	private static final int E_MASK = 0b100000000;
	public static void handle(short s) {
		int address = (s >> 9) & 0b111;
		if ((s & E_MASK) == 0) { // e == 0
			// MOVI
			setReg(address, (byte)s);
		} else {
			// MOVHI
			setReg(address, (short)(getReg(address) | (s << 8)));
		}
	}
}
