package sisc;

public final class Memory implements InstructionStorage {
	private final byte[] s;

	/**
	 * Places all bytes in the passed array into this memory, placing them starting at {@code startPos} and going for
	 * the length of the array. Throws an exception if the array is too big. The rest of bytes are kept the same.
	 * @param bytes The array of bytes to flash from
	 * @param startPos The starting position to flash to
	 */
	public void flash(byte[] bytes, int startPos) {
		System.arraycopy(bytes, 0, s, startPos, bytes.length);
	}

	public Memory() {
		this.s =  new byte[Character.MAX_VALUE];
	}

	@Override
	public short getInstructionAt(char address) {
		return (short)SHORT_AT.get(s, address);
	}

	public short load(short addr) {
		int safeAddr = clean(addr);
		return (short)SHORT_AT.get(s, safeAddr);
	}

	public short loadByte(short addr) {
		return s[addr];
	}

	public void store(short addr, short value) {
		int safeAddr = clean(addr);
		SHORT_AT.set(s, safeAddr, value);
	}

	public void storeByte(short addr, byte val) {
		s[addr] = val;
	}

	private int clean(short addr) {
		return Short.toUnsignedInt(addr) & ~0b1;
	}
}
