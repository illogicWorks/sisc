package sisc;

public class PC {
	private short PC = 0;
	
	public short next() {
		if(PC < 0) PC = 0;
		
		return PC++;
	}
	
	public void jumpOffset(byte amount) {
		PC += amount;
	}
	
	public void jumpTo(short dest) {
		PC = --dest; // next() call will increment
	}
	
	/**
	 * @return The next instruction to execute, without incrementing the counter
	 */
	public short peek() {
		if (PC < 0) {
			return 0;
		}
		return PC;
	}
	
	public String peekStr() {
		return Integer.toHexString(Short.toUnsignedInt(PC));
	}
}
