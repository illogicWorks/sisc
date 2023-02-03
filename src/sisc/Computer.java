package sisc;

import sisc.instructions.*;
import sisc.io.DeviceDiscoverer;
import sisc.io.IOSystem;

import static sisc.instructions.Instructions.*;

public class Computer {
	private final Memory memory = new Memory();
	private final InstructionStorage instructions;
	private final PC PC = new PC();
	private final IOSystem ioSystem;
	
	public Computer() {
		instructions = new IMEM(FileReader.loadImage("samples/test.siso"));
		ioSystem = new IOSystem();
		DeviceDiscoverer.discoverDevices(ioSystem);
	}
	
	/**
	 * Runs all the code in the {@link InstructionStorage}. Infinite loop
	 */
	public void run() {
		System.out.println("IMEM contents: " + instructions);
		
		while (true) {
            System.out.println("PC addr: " + PC.peekStr());
			runNext();
		}
	}

	/**
	 * Runs a single instruction
	 */
	public void runNext() {
		execute(instructions.getInstructionAt(PC.next()));
	}

	private void execute(short instr) {
		byte instrType = (byte)(instr >> 12 << 4);
		switch (instrType) {
			case OPS  -> LogicArithmetic.handle(instr);
			case CMP  -> Compare.handle(instr);
			case ADDI -> Immediate.addi(instr);
			case LD, ST, LDB, STB -> MemoryInstr.handle(instr, memory);
			case JUMP -> Branching.branch(instr, PC);
			case JALR -> Branching.jalr(instr, PC);
			case MOVE -> Moving.handle(instr);
			case IO   -> InputOutput.handle(instr, ioSystem);
			default   -> unknown(instrType);
		};
	}
	
	private static void unknown(byte instr) {
		System.out.println("Unknown instruction: " + Integer.toHexString(Byte.toUnsignedInt(instr)) + ", considering as NOP");
	}
}
