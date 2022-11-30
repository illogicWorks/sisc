package sisc;

import sun.misc.Unsafe;

/**
 * A very safe implementation of the computer's memory module
 * @author altrisi
 */
public record Memory(byte[] s) implements InstructionStorage {
	private static final Memory INSTANCE = new Memory(new byte[Short.MAX_VALUE * 2]);
	private static final long BYTE_ARRAY_BASE = Unsafe.ARRAY_BYTE_BASE_OFFSET;
	private static final long BYTE_ARRAY_SCALE = Unsafe.ARRAY_BYTE_INDEX_SCALE;
	private static final Unsafe UNSAFE;
	static {
		Unsafe u;
		try {
			var m = Unsafe.class.getDeclaredField("theUnsafe");
			m.setAccessible(true);
			u = (Unsafe)m.get(null);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException("This wasn't safe anyway!", e);
		}
		UNSAFE = u;
	}

	/**
	 * Places all bytes in the passed array into this memory, placing them starting at {@code startPos} and going for
	 * the length of the array. Throws an exception if the array is too big. The rest of bytes are kept the same.
	 * @param bytes The array of bytes to flash from
	 * @param startPos The starting position to flash to
	 * @return The {@link Memory} instance
	 */
	public static Memory flash(byte[] bytes, int startPos) {
		System.arraycopy(bytes, 0, INSTANCE.s(), startPos, bytes.length);
		return INSTANCE;
	}

	@Override
	public short getInstructionAt(short address) {
		throw new UnsupportedOperationException("Not implemented! (yet)");
	}

	public short load(short addr) {
		int safeAddr = clean(addr);
		/*short ret = 0;
		ret |= Byte.toUnsignedInt(s[safeAddr]);
		ret |= s[safeAddr + 1] << 8;*/
		return UNSAFE.getShort(s, BYTE_ARRAY_BASE + safeAddr * BYTE_ARRAY_SCALE);
	}

	public short loadByte(short addr) {
		return s[addr];
	}

	public void store(short addr, short value) {
		int safeAddr = clean(addr);
		/*byte lower  = (byte)value;
		byte higher = (byte)(value >> 8);
		s[safeAddr] = lower;
		s[safeAddr+1] = higher;*/
		UNSAFE.putShort(s, BYTE_ARRAY_BASE + safeAddr * BYTE_ARRAY_SCALE, value);
	}

	public void storeByte(short addr, byte val) {
		s[addr] = val;
	}

	private int clean(short addr) {
		return Short.toUnsignedInt(addr) & ~0b1;
	}
}
