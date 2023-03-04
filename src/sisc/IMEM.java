package sisc;

public record IMEM(short[] data) implements InstructionStorage {
	@Override
	public String toString() {
		String s = "";
		for (short instr : data) {
			String str = Integer.toHexString(Short.toUnsignedInt(instr));
			s += "0".repeat(4 - str.length()) + str;
		}
		return s;
	}
	
	@Override
	public short getInstructionAt(char offset) {
		return data[offset];
	}
}
