package sisc;

public record IMEM(short[] instructions) {
	public String toString() {
		String s = "";
		for(short instr : instructions) {
			s += Integer.toHexString(Short.toUnsignedInt(instr));
		}
		return s;
	}
}
