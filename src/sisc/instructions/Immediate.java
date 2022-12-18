package sisc.instructions;

import static sisc.RegFile.*;
import static sisc.instructions.Instructions.*;

public class Immediate {
	private static final int IMMEDIATE_MASK = 0b111111;
	public static void handle(short s) {
		int regA = (s >> 9) & REG_MASK;
		int dest = (s >> 6) & REG_MASK;
		int immediate = s & IMMEDIATE_MASK;
		setReg(dest, (short)(getReg(regA) + immediate));
	}
}
