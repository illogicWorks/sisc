package sisc;

public class PC {
	private static short PC = 0;
	
	public static short next() {
		if(PC < 0) PC = 0;
		
		return PC++;
	}
	
	public static void jumpOffset(byte amount) {
		PC += amount;
	}
	
	public static void jumpTo(short dest) {
		PC = --dest; // next() call will increment
	}
	
	/**
	 * @return The next instruction to execute, without incrementing the counter
	 */
	public static short peek() {
		if (PC < 0) {
			return 0;
		}
		return PC;
	}
	
	public static String peekStr() {
		return Integer.toHexString(Short.toUnsignedInt(PC));
	}
}
