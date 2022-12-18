package sisc;

public class Computer {
	private final InstructionStorage instructions;
	
	public Computer() {
		this.instructions = new IMEM(FileReader.loadImage("samples/test.siso"));
	}
	
	public void run() {
		
		System.out.println("IMEM contents: " + instructions);
		
		while (true) {
            System.out.println("PC addr: " + PC.peekStr());
			InstructionParser.execute(instructions.getInstructionAt(PC.next()));
		}
	}
}
