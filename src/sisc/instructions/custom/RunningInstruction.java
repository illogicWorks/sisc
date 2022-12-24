package sisc.instructions.custom;

import sisc.PC;

import static sisc.RegFile.*;
import static sisc.instructions.Instructions.REG_MASK;

import sisc.Memory;

public class RunningInstruction {
	private final FullInstruction instr;
	private final short code;
	private short rAt;
	private short newrAt;
	public RunningInstruction(short code, FullInstruction runnable) {
		this.code = code;
		this.instr = runnable;
	}
	private static final int MASK3 = 0b111;
	private static final int MASK8 = 0b11111111;
	public void execute() {
		newrAt = (short)(PC.peek() + (byte)(code & MASK8));
		for (InstructionForm i : instr.roms()) {
			rAt = newrAt;
			short valA = getReg((code >> 9) & REG_MASK);
			short valB;
			if (i.RyN()) {
				valB = getReg((code >> 6) & REG_MASK);
			} else {
				valB = switch (i.mxN()) {
					case SE6 -> throw new UnsupportedOperationException("ouch");
					case SE8 -> (byte)code;
					case SE8_SL1 -> (short)(((byte)code) << 1);
					case TWO -> 2;
				};
			}
			int f = i.MxF() ? i.F() : (code & MASK3);
			AluEmulatorResult alu = AluEmulatorResult.calculate(i.OP(), f, valA, valB);
			newrAt = alu.result();
			if (alu.zero() && i.Bz() || !alu.zero() && i.Bnz()) {
				if (i.AluRAt()) {
					PC.jumpTo(alu.result());
				} else {
					PC.jumpTo(rAt);
				}
			}
			if (i.LdIr()) {
				throw new UnsupportedOperationException();
			}
			if (i.WrD()) {
				setReg(i.mxD().addrD(code), switch (i.pila()) {
					case PC -> PC.peek();
					case ALU -> alu.result();
					case IN -> {
						if (!i.RdIn()) {
							throw new IllegalStateException();
						}
						// TODO I/O
						throw null;
					}
					case MEM -> {
						short memAddr = i.RAtPc() ? rAt : PC.peek();
						if (i.Byte()) {
							yield Memory.flash(new byte[0], 0)
									.loadByte(memAddr);
						} else {
							yield Memory.flash(new byte[0], 0)
									.load(memAddr);
						}
					}
				});
			}
		}
	}
}
