package sisc.instructions;

import static sisc.RegFile.getReg;
import static sisc.RegFile.setReg;
import static sisc.instructions.Instructions.*;

public class Compare {
	private static final int REG_MASK = 0b111;

	public static void handle(short s) {
		byte instr = (byte)(s & 0b111);
		int regA = (s >> 9) & REG_MASK;
		int regB = (s >> 6) & REG_MASK;
		int dest = (s >> 3) & REG_MASK;
		eval(regA, regB, dest, switch (instr) {
			case CMPLT  -> (a, b) -> a < b;
			case CMPLE  -> (a, b) -> a <= b;
			case CMPEQ  -> (a, b) -> a == b;
			case CMPLTU -> (a, b) -> Short.compareUnsigned(a, b) < 0;
			case CMPLEU -> (a, b) -> Short.compareUnsigned(a, b) <= 0;
			default -> throw new UnsupportedOperationException();
		});
	}

	private static void eval(int regA, int regB, int dest, BiShort2BoolFunction fn) {
		setReg(dest, (short)(fn.eval(getReg(regA), getReg(regB)) ? 1 : 0));
	}

	private interface BiShort2BoolFunction {
		boolean eval(short a, short b);
	}
}
