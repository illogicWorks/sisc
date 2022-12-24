package sisc.instructions.custom;

import sisc.instructions.custom.Muxes.*;

public class CustomParser {
	private static final int MASK_2 = 0b11;
	private static final int MASK_3 = 0b111;
	
	public static InstructionForm parseRomOut(int row) {
		return new InstructionForm(
				MxD.vals[row & MASK_2],
				b(row >> 2 & MASK_3),
				bool(row >> 5),
				MxN.vals[row >> 6 & MASK_2],
				b(row >> 8 & MASK_3),
				PILA.vals[row >> 11 & MASK_2],
				bool(row >> 12),
				bool(row >> 13),
				bool(row >> 14),
				bool(row >> 15), 
				bool(row >> 16),
				bool(row >> 17),
				bool(row >> 18),
				bool(row >> 19),
				bool(row >> 20),
				bool(row >> 21),
				bool(row >> 22),
				bool(row >> 23));
	}

	private static boolean bool(int i) {
		return (i & 1) == 1 ? true : false;
	}

	private static byte b(int i) {
		return (byte)i;
	}
}
