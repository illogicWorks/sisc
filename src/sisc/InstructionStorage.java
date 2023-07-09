package sisc;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;

public interface InstructionStorage {
	VarHandle SHORT_AT = MethodHandles.byteArrayViewVarHandle(short[].class, ByteOrder.LITTLE_ENDIAN);
	short getInstructionAt(char address);
}
