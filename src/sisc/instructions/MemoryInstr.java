package sisc.instructions;

import sisc.Memory;

import static sisc.RegFile.*;
import static sisc.instructions.Instructions.*;

public class MemoryInstr {
	private static final Memory MEM = Memory.flash(new byte[0], 0);
	private static final int REG_MASK = 0b111;

	public static void handle(short s) {
		int regAddress = (s >> 9) & REG_MASK;
		int regAux     = (s >> 6) & REG_MASK;
		short constant = extractConstant(s);
		short address  = (short)(getReg(regAddress) + constant);

		switch (s >> 12 << 4) {
			case LD -> setReg(regAux, MEM.load(address));
			case ST -> MEM.store(address, getReg(regAux));
			case LDB -> setReg(regAux, MEM.loadByte(address));
			case STB -> MEM.storeByte(address, (byte)getReg(regAux));
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
