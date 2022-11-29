package sisc.instructions;

import static sisc.RegFile.*;
import static sisc.instructions.Instructions.*;

public class LogicArithmetic {
	private static final int REG_MASK = 0b111;

	public static void handle(short s) {
		byte instr = (byte)(s & 0b111);
		int regA = (s >> 9) & REG_MASK;
		int regB = (s >> 6) & REG_MASK;
		int dest = (s >> 3) & REG_MASK;
		eval(regA, regB, dest, switch (instr) {
			case AND -> (a, b) -> a & b;
			case OR  -> (a, b) -> a | b;
			case XOR -> (a, b) -> a ^ b;
			case NOT -> (a, b) -> ~a;
			case ADD -> (a, b) -> a + b;
			case SUB -> (a, b) -> a - b;
			case SHA -> LogicArithmetic::sha;
			case SHL -> LogicArithmetic::shl;
			default -> throw new UnsupportedOperationException();
		});
	}

	private static final int SHIFT_SIGN   = 0b10000;
	private static final int SHIFT_AMOUNT = 0b01111;

	private static int shl(short a, short b) {
		if ((b & SHIFT_SIGN) == 0) { // > 0
			return a << (b & SHIFT_AMOUNT);
		} else {
			return a >>> (b & SHIFT_AMOUNT);
		}
	}

	private static int sha(short a, short b) {
		if ((b & SHIFT_SIGN) == 0) { // > 0
			return a << b;
		} else {
			return a >> (b & SHIFT_AMOUNT);
		}
	}

	private static void eval(int regA, int regB, int dest, BiShort2ShortFunction fn) {
		setReg(dest, (short)fn.eval(getReg(regA), getReg(regB)));
	}

	private interface BiShort2ShortFunction {
		int eval(short a, short b);
	}
}
