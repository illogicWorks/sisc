package sisc;

public record IMEM(short[] data) implements InstructionStorage {
	@Override
	public String toString() {
		String s = "";
		for (short instr : data) {
			s += Integer.toHexString(Short.toUnsignedInt(instr));
		}
		return s;
	}
	
	@Override
	public short getInstructionAt(short offset) {
		return data[offset];
	}
}
