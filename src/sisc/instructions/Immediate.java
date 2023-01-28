package sisc.instructions;

import static sisc.RegFile.*;
import static sisc.instructions.Instructions.*;

public class Immediate {
	public static void addi(short s) {
		int regA = (s >> 9) & REG_MASK;
		int dest = (s >> 6) & REG_MASK;
		int immediate = extract6BitConstant(s);
		setReg(dest, (short)(getReg(regA) + immediate));
	}
}
