package sisc;

import sisc.instructions.*;

import static sisc.instructions.Instructions.*;

public class InstructionParser {	
	public static void execute(short s) {
		byte instr = (byte)(s >> 12 << 4);
		switch (instr) {
			case OPS  -> LogicArithmetic.handle(s);
			case CMP  -> Compare.handle(s);
			case ADDI -> throw todo(instr);
			case LD, ST, LDB, STB -> MemoryInstr.handle(s);
			case JALR -> throw todo(instr);
			case MOVE -> Moving.handle(s);
			case IO   -> throw todo(instr);
			default   -> unknown(instr);
		};
	}

	private static UnsupportedOperationException todo(byte instr) {
		return new UnsupportedOperationException("TODO: " + Integer.toHexString(Byte.toUnsignedInt(instr)));
	}

	private static void unknown(byte instr) {
		System.out.println("Unknown instruction: " + Integer.toHexString(Byte.toUnsignedInt(instr)));
	}
}
