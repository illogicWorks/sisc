package sisc;

public record IMEM(short[] data) {
	public String toString() {
		String s = "";
		for(short instr : data) {
			s += Integer.toHexString(Short.toUnsignedInt(instr));
		}
		return s;
	}
	
	public short getInstructionAt(short offset) {
		return data[offset];
	}
}
