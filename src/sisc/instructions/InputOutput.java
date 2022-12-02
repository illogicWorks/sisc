package sisc.instructions;

import sisc.io.IOSystem;
import static sisc.RegFile.*;

public class InputOutput {
	public static void handle(short s, IOSystem io) {
		boolean doOut = (s & 0b100000000) != 0;
		int reg = (s >> 9) & 0b111;
		byte addr = (byte)(s >> 8);
		if (doOut) {
			io.setOut(addr, getReg(reg));
		} else {
			setReg(reg, io.getIn(addr));
		}
	}
}
