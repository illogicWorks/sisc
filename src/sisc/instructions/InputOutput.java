package sisc.instructions;

import sisc.io.IOSystem;

import static sisc.instructions.Instructions.*;
import static sisc.RegFile.*;

public class InputOutput {
	public static void handle(short s, IOSystem io) {
		boolean doOut = (s & E_MASK) != 0;
		int reg = (s >> 9) & REG_MASK;
		byte addr = (byte)(s & 0xFF);
		if (doOut) {
			io.setOut(addr, getReg(reg));
		} else {
			setReg(reg, io.getIn(addr));
		}
	}
}
