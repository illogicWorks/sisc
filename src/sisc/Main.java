package sisc;

public class Main {

	FileReader fileReader;
	IMEM imem;
	
	private final short PC = 0;
	
	public static void main(String[] args) {
		System.out.println("Hello World!");
		new Main().run();
	}
	
	public void run() {
		imem = new IMEM(FileReader.loadImage("samples/test.siso"));
		System.out.println("IMEM contents: " + imem);
		
		InstructionParser.execute(imem.getInstructionAt(PC));
	}

}
