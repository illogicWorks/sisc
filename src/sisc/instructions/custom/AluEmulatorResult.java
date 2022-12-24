package sisc.instructions.custom;

import static sisc.instructions.Instructions.*;

public record AluEmulatorResult(short result) {
	// this is bad... almost copy of LA and CMP
	public static AluEmulatorResult calculate(int op, int f, short a, short b) {
		return new AluEmulatorResult((short) switch (op) {
			case OPS -> switch (f) {
				case AND -> a & b;
				case OR  -> a | b;
				case XOR -> a ^ b;
				case NOT -> ~a;
				case ADD -> a + b;
				case SUB -> a - b;
				case SHA -> sha(a, b);
				case SHL -> shl(a, b);
				default -> throw null;
			};
			case CMP -> switch (f) {
				case CMPLT  -> a < b;
				case CMPLE  -> a <= b;
				case CMPEQ  -> a == b;
				case CMPLTU -> Short.compareUnsigned(a, b) < 0;
				case CMPLEU -> Short.compareUnsigned(a, b) <= 0;
				default -> throw null;
			};
			default -> switch (f) {
				case 0 -> a;
				case 1 -> b;
				case 2 -> movhi(a, b);
				default -> a & ~1;
			};
		});
	}

	public boolean zero() {
		return result == 0;
	}

	private static final int MASK8 = 0b11111111;
	private static short movhi(short a, short b) {
		return (short)((a & MASK8) | (b << 8));
	}
	
	private static final int SHIFT_SIGN   = 0b10000;
	private static final int SHIFT_AMOUNT = 0b01111;

	private static int shl(short a, short b) {
		if ((b & SHIFT_SIGN) == 0) { // > 0
			return a << (b & SHIFT_AMOUNT);
		} else {
			return a >>> (b & SHIFT_AMOUNT);
		}
	}

	private static int sha(short a, short b) {
		if ((b & SHIFT_SIGN) == 0) { // > 0
			return a << b;
		} else {
			return a >> (b & SHIFT_AMOUNT);
		}
	}
}
