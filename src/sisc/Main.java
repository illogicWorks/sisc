package sisc;

public class Main {

	FileReader fileReader;
	
	public static void main(String[] args) {
		System.out.println("Hello World!");
		new Main().run();
	}
	
	public void run() {
		fileReader = new FileReader();
		fileReader.LoadImage("samples/test.siso");
	}

}
