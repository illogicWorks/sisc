package sisc.instructions;

import static sisc.RegFile.*;
import static sisc.instructions.Instructions.*;

import sisc.PC;

public class Branching {
	public static void branch(short s, PC pc) {
		int address = (s >> 9) & REG_MASK;
		if ((s & E_MASK) == 0) { // e == 0
			// BZ
			if (getReg(address) == 0) pc.jumpOffset((byte)s);
		} else {
			// BNZ
			if (getReg(address) != 0) pc.jumpOffset((byte)s);
		}
	}

	public static void jalr(short s, PC pc) {
		int regA = (s >> 11) & REG_MASK;
		int regD = (s >> 8) & REG_MASK;
		setReg(regD, (short)pc.peek());
		pc.jumpTo((char)getReg(regA));
	}
}
