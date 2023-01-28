package sisc.instructions;

import sisc.Memory;

import static sisc.RegFile.*;
import static sisc.instructions.Instructions.*;

public class MemoryInstr {
	public static void handle(short s, Memory mem) {
		int regAddress = (s >> 9) & REG_MASK;
		int regAux     = (s >> 6) & REG_MASK;
		short constant = extractConstant(s);
		short address  = (short)(getReg(regAddress) + constant);

		switch (s >> 12 << 4) {
			case LD -> setReg(regAux, mem.load(address));
			case ST -> mem.store(address, getReg(regAux));
			case LDB -> setReg(regAux, mem.loadByte(address));
			case STB -> mem.storeByte(address, (byte)getReg(regAux));
			default -> throw new IllegalArgumentException();
		}
	}

	private static final int MASK_6BIT_SIGN = 0b100000;
	private static final int MASK_6BIT_POS  = 0b111111; // used with &
	private static final byte MASK_6BIT_NEG = (byte)0b11000000; // used with |
	private static short extractConstant(short s) {
		byte b = (byte)s;
		if ((b & MASK_6BIT_SIGN) == 0) { // > 0
			return (short)(b & MASK_6BIT_POS);
		} else {
			return (short)(b | MASK_6BIT_NEG);
		}
	}
}
