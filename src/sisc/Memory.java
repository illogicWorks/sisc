package sisc;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;

public record Memory(byte[] s) implements InstructionStorage {
	private static final VarHandle SHORT_AT = MethodHandles.byteArrayViewVarHandle(short[].class, ByteOrder.LITTLE_ENDIAN);
	private static final Memory INSTANCE = new Memory(new byte[Short.MAX_VALUE * 2]);

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
