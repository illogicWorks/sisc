package sisc.instructions;

// from SISAj, added reg and e masks
// TODO these could be enums
public interface Instructions {
	// CCCC field in the MSB
	byte OPS  = (byte)0b00000000;
	byte CMP  = (byte)0b00010000;
	
	byte ADDI = (byte)0b00100000;
	// memory start
	byte LD   = (byte)0b00110000;
	byte ST   = (byte)0b01000000;
	byte LDB  = (byte)0b01010000;
	byte STB  = (byte)0b01100000;
	// memory end
	byte JALR = (byte)0b01110000; // new!
	byte JUMP = (byte)0b10000000;
	byte MOVE = (byte)0b10010000;
	byte IO   = (byte)0b10100000;
	
	// FFF field in the LSB
	// OPS
	byte AND = 0;
	byte OR  = 1;
	byte XOR = 2;
	byte NOT = 3;
	byte ADD = 4;
	byte SUB = 5;
	byte SHA = 6;
	byte SHL = 7;
	
	// CMPS
	byte CMPLT  = 0;
	byte CMPLE  = 1;
	byte CMPEQ  = 3;
	byte CMPLTU = 4;
	byte CMPLEU = 5;
	
	// from sisc: masks
	int REG_MASK = 0b111;
	int E_MASK = 0b100000000;
	
	// from sisc: 6 bit constants
	static short extract6BitConstant(short s) {
		// here so we don't expose them, they're still constants
		final int MASK_6BIT_SIGN = 0b100000;
		final int MASK_6BIT  = 0b111111;
		byte b = (byte)s;
		if ((b & MASK_6BIT_SIGN) == 0) { // > 0
			return (short)(b & MASK_6BIT);
		} else {
			return (short)(b | ~MASK_6BIT);
		}
	}
}
