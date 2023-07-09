package sisc;

public class Main {
	public static void main(String[] args) {
		System.out.println("SISC Emulator v1.0");
		
		if (args.length < 2) return;
		
		final String mode = args[0].toUpperCase();
		
		if (args[1].isBlank()) throw new IllegalArgumentException("Invalid object file path.");
		
		new Computer(switch(mode) {
			case "VN" -> true;
			case "HAR" -> false;
			default -> throw new IllegalArgumentException("Invalid computer mode.");
		}, FileReader.loadImageBytes(args[1])).run();
	}

}
