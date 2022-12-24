package sisc.instructions.custom;

import java.util.Objects;

import sisc.instructions.custom.Muxes.*;

public record InstructionForm(
		MxD mxD,
		byte F,
		boolean MxF,
		MxN mxN,
		byte OP,
		PILA pila,
		boolean RyN,
		boolean PcRx,
		boolean AluRAt,
		boolean RAtPc,
		boolean Byte,
		boolean LdIr,
		boolean WrD,
		boolean WrOut,
		boolean RdIn,
		boolean WrMem,
		boolean Bz,
		boolean Bnz
		)
{
	public InstructionForm {
		Objects.requireNonNull(mxD);
		Objects.requireNonNull(mxN);
		Objects.requireNonNull(pila);
	}
}
