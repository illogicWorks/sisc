package sisc.instructions.custom;

public final class Muxes {
	public enum PILA {
		ALU, MEM, IN, PC;
		static final PILA[] vals = values();
	}

	public enum MxD {
		/**
		 * The lowest bits D can be in. That is: b4, b3 and b2
		 */
		LOWER(3),
		/**
		 * The middle bits D can be in. That is: b7, b6 and b5
		 */
		MIDDLE(6),
		/**
		 * The highest bits D can be in. That is: b10, b19 and b8
		 */
		HIGHEST(9);
		private final int off;
		private MxD(int off) {
			this.off = off;
		}
		public int addrD(short instr) {
			return (instr >> off) & 0b111;
		}

		static final MxD[] vals = values();
	}

	public enum MxN {
		SE6, SE8, SE8_SL1, TWO;
		static final MxN[] vals = values();
	}
}
