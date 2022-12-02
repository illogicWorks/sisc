package sisc;

public class RegFile {
	private static final short[] REGS = new short[8];
	
	public static short getReg(int address) {
		return REGS[address];
	}
	
	public static void setReg(int address, short value) {
		REGS[address] = value;
	}
	
	public static void PrintRegisters() {
		for(int i = 0; i < REGS.length; i++) {
			System.out.println("R"+ i + "=" + RegFile.getReg(i));
		}
	}
}
