package sisc;

import java.util.BitSet;

public class Main {

	FileReader fileReader;
	IMEM imem;
	
	public static void main(String[] args) {
		System.out.println("Hello World!");
		new Main().run();
	}
	
	public void run() {
		imem = new IMEM(FileReader.LoadImage("samples/test.siso"));
		System.out.println(imem);
	}

}
