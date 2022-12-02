package sisc;

public class Debugger {

	private static boolean active = false;
	
	public static void setActive(boolean opt) {
		active = opt;
	}
	
	public static void run() {
		if(active) {
			// Do the funny stuff
		}
	}
}
