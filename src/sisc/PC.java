package sisc;

public class PC {
	private static short PC = 0;
	
	public static short next() {
		if(PC < 0) PC = 0;
		
		return PC++;
	}
	
	public static void jump(byte amount) {
		PC += amount;
	}
	
	public static String peek() {
		return Integer.toHexString(Short.toUnsignedInt(PC));
	}
}
