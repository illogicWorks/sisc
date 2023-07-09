package sisc;

public record IMEM(byte[] data, int filledLength) implements InstructionStorage {
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < filledLength; i++) {
			String str = Integer.toHexString(Short.toUnsignedInt((short)SHORT_AT.get(data, i * 2)));
			builder.append("0".repeat(4 - str.length()));
			builder.append(str);
		}
		return builder.toString();
	}
	
	@Override
	public short getInstructionAt(char offset) {
		return data[offset];
	}
}
