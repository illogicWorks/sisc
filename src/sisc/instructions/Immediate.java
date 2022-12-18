package sisc.instructions;

import static sisc.RegFile.*;
import static sisc.instructions.Instructions.*;

public class Immediate {
	private static final int IMMEDIATE_MASK = 0b111111;
	public static void handle(short s) {
		byte instr = (byte)(s >> 12 << 4);
		int regA = (s >> 9) & REG_MASK;
		System.out.println("regA: " + regA);
		int dest = (s >> 6) & REG_MASK;
		int immediate = s & IMMEDIATE_MASK;
		eval(regA, immediate, dest, switch (instr) {
			case ADDI -> (a, n) -> a + n;
			default -> throw new UnsupportedOperationException();
		});
	}

	private static void eval(int regA, int immediate, int dest, BiShort2ShortFunction fn) {
		setReg(dest, (short)fn.eval(getReg(regA), (byte)immediate));
	}

	private interface BiShort2ShortFunction {
		int eval(short a, byte b);
	}
}
