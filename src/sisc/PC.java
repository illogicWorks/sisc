package sisc;

public class PC {
	private char PC = 0;
	
	public char next() {
		char ret = PC;
		PC += 2;
		return ret;
	}
	
	public void jumpOffset(byte amount) {
		PC += amount * 2;
	}
	
	public void jumpTo(char dest) {
		PC = (char)(dest - 2); // next() call will increment
	}
	
	/**
	 * @return The next instruction to execute, without incrementing the counter
	 */
	public char peek() {
		return PC;
	}
	
	public String peekStr() {
		return Integer.toHexString(PC);
	}
}
