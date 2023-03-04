package sisc;

public class PC {
	private char PC = 0;
	
	public char next() {
		return PC++;
	}
	
	public void jumpOffset(byte amount) {
		PC += amount;
	}
	
	public void jumpTo(char dest) {
		PC = --dest; // next() call will increment
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
