package sisc.instructions;

import static sisc.RegFile.*;
import static sisc.instructions.Instructions.*;

public class Moving {
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
