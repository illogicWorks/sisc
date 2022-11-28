package sisc;

import static sisc.Instructions.*;

public class InstructionParser {
	public static void main(String[] args) {
		execute((short)(0b0110 << 12));
	}
	
	public static void execute(short s) {
		byte instr = (byte)(s >> 12 << 4);
		System.out.println(switch (instr) {
			case OPS  -> "AL";
			case CMP  -> "CMP";
			case ADDI -> "ADDI";
			case LD   -> "LD";
			case ST   -> "ST";
			case LDB  -> "LDB";
			case STB  -> "STB";
			case JALR -> "JALR";
			case MOVE -> "MOVE";
			case IO   -> "IO";
			default -> null;
		});
	}
}
