package sisc.instructions;

import static sisc.RegFile.*;

import sisc.PC;

public class Branching {
	private static final int E_MASK = 0b100000000;
	public static void branch(short s) {
		int address = (s >> 9) & 0b111;
		if ((s & E_MASK) == 0) { // e == 0
			// BZ
			if (getReg(address) == 0) PC.jumpOffset((byte)s);
		} else {
			// BNZ
			if (getReg(address) != 0) PC.jumpOffset((byte)s);
		}
	}
	private static final int REG_MASK = 0b111;
	public static void jalr(short s) {
		int regA = (s >> 11) & REG_MASK;
		int regD = (s >> 8) & REG_MASK;
		setReg(regD, PC.peek());
		PC.jumpTo(getReg(regA));
	}
}
