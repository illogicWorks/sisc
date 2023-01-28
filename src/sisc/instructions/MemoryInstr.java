package sisc.instructions;

import sisc.Memory;

import static sisc.RegFile.*;
import static sisc.instructions.Instructions.*;

public class MemoryInstr {
	public static void handle(short s, Memory mem) {
		int regAddress = (s >> 9) & REG_MASK;
		int regAux     = (s >> 6) & REG_MASK;
		short constant = extract6BitConstant(s);
		short address  = (short)(getReg(regAddress) + constant);

		switch (s >> 12 << 4) {
			case LD -> setReg(regAux, mem.load(address));
			case ST -> mem.store(address, getReg(regAux));
			case LDB -> setReg(regAux, mem.loadByte(address));
			case STB -> mem.storeByte(address, (byte)getReg(regAux));
			default -> throw new IllegalArgumentException();
		}
	}
}
