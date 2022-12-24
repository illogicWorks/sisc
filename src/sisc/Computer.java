package sisc;

import sisc.instructions.*;

import static sisc.instructions.Instructions.*;

public class Computer {
	private final InstructionStorage instructions;
	
	public Computer() {
		this.instructions = new IMEM(FileReader.loadImage("samples/test.siso"));
	}
	
	public void run() {
		
		System.out.println("IMEM contents: " + instructions);
		
		while (true) {
            System.out.println("PC addr: " + PC.peekStr());
			execute(instructions.getInstructionAt(PC.next()));
		}
	}

	// TODO make non-static and use stuff from the Computer instance once it's not so static
	public static void execute(short instr) {
		byte instrType = (byte)(instr >> 12 << 4);
		switch (instrType) {
			case OPS  -> LogicArithmetic.handle(instr);
			case CMP  -> Compare.handle(instr);
			case ADDI -> Immediate.handle(instr);
			case LD, ST, LDB, STB -> MemoryInstr.handle(instr);
			case JUMP -> Branching.branch(instr);
			case JALR -> Branching.jalr(instr);
			case MOVE -> Moving.handle(instr);
			case IO   -> throw new UnsupportedOperationException("TODO: I/O");
			default   -> unknown(instrType);
		};
	}
	
	private static void unknown(byte instr) {
		System.out.println("Unknown instruction: " + Integer.toHexString(Byte.toUnsignedInt(instr)) + ", considering as NOP");
	}
}
