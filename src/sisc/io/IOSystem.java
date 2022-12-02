package sisc.io;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.HashMap;
import java.util.Map;

import sisc.api.io.PortInUseException;

public final class IOSystem {
	private static final VarHandle HANDLE = MethodHandles.arrayElementVarHandle(short[].class);
	private final short[] input = new short[256];
	private final short[] output = new short[256];
	private final PortChangeListener[] outChangeListeners = new PortChangeListener[256];
	private final PortReadListener[] inReadListeners = new PortReadListener[256];
	private final Map<Integer, InputDeviceController> usedIn = new HashMap<>();
	private final Map<Integer, InputDeviceController> usedOut = new HashMap<>();

	public void setOut(int port, short value) {
		HANDLE.setVolatile(output, port, value);
		PortChangeListener listener = outChangeListeners[port];
		if (listener != null)
			listener.onPortChanged(value);
	}
	
	public short getIn(int port) {
		short ret = (short)HANDLE.getVolatile(input, port);
		PortReadListener listener = inReadListeners[port];
		if (listener != null)
			listener.onPortRead();
		return ret;
	}

	// methods only devices can touch
	synchronized void registerInput(InputDeviceController device) throws PortInUseException {
		// Separate to not occupy some ports if a later one fails
		if (usedIn.containsKey(device.requestPort))
			throw new PortInUseException(device.requestPort);
		if (usedOut.containsKey(device.acknowledgePort))
			throw new PortInUseException(device.acknowledgePort);
		if (usedOut.containsKey(device.dataPort))
			throw new PortInUseException(device.dataPort);

		usedIn.put(device.requestPort, device);
		usedOut.put(device.acknowledgePort, device);
		usedIn.put(device.dataPort, device);
		inReadListeners[device.dataPort] = device;
		VarHandle.fullFence();
	}

	synchronized void unregisterInput(InputDeviceController device) {
		usedIn.remove(device.requestPort);
		usedOut.remove(device.acknowledgePort);
		usedIn.remove(device.dataPort);
		inReadListeners[device.dataPort] = null;
		VarHandle.fullFence();
	}

	void setIn(int port, short value) {
		HANDLE.setVolatile(input, port, value);
		//input[port] = value;
	}

	void setIn(int port, boolean value) {
		setIn(port, (short)(value ? 1 : 0));
	}
	
	short getOut(int port) {
		return (short)HANDLE.getVolatile(output, port);
	}
	
	@Override
	public String toString() {
		return "IOSystem=[inPorts=" + usedIn + ", outPorts=" + usedOut + "]";
	}
}
