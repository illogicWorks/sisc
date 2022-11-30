package sisc;

public class Main {

	FileReader fileReader;
	InstructionStorage imem;
	
	public static void main(String[] args) {
		System.out.println("Hello World!");
		new Main().run();
	}
	
	public void run() {
		imem = new IMEM(FileReader.loadImage("samples/test.siso"));
		System.out.println("IMEM contents: " + imem);
		System.out.println("PC addr: " + PC.peek());
		// RIght now we are executing the first instruction pointed by PC
		InstructionParser.execute(imem.getInstructionAt(PC.next()));		
	}

}
