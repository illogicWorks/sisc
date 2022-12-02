package sisc;

import sisc.instructions.*;

import static sisc.instructions.Instructions.*;

public class InstructionParser {	
	public static void execute(short instr) {
		byte instrType = (byte)(instr >>> 12 << 4);
		switch (instrType) {
			case OPS  -> LogicArithmetic.handle(instr);
			case CMP  -> Compare.handle(instr);
			case ADDI -> Immediate.handle(instr);
			case LD, ST, LDB, STB -> MemoryInstr.handle(instr);
			case JALR -> throw todo(instrType);
			case MOVE -> Moving.handle(instr);
			case IO   -> InputOutput.handle(instr, null); //TODO hook IOSystem
			default   -> unknown(instrType);
		};
	}

	private static UnsupportedOperationException todo(byte instr) {
		return new UnsupportedOperationException("TODO: " + Integer.toHexString(Byte.toUnsignedInt(instr)));
	}

	private static void unknown(byte instr) {
		System.out.println("Unknown instruction: " + Integer.toHexString(Byte.toUnsignedInt(instr)));
	}
}
