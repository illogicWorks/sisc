package sisc.io;

import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.LockSupport;

import sisc.api.io.OutputDevice;
import sisc.api.io.OutputPair;
import sisc.api.io.PortInUseException;

public final class OutputDeviceController implements OutputPair, PortWriteListener {
	private final OutputDevice device;
	private final IOSystem system;
	private boolean connected;
	private volatile Thread lockedThread;
	
	int requestPort;
	int dataPort;
	
	public OutputDeviceController(IOSystem system, OutputDevice device) {
		this.device = device;
		this.system = system;
	}
	
	@Override
	public void onPortWrite(short value) {
		system.setIn(requestPort, false);
		LockSupport.unpark(lockedThread);
		lockedThread = null;
	}

	@Override
	public void connectTo(int request, int acknowledge, int data) throws PortInUseException {
		if (connected)
			throw new IllegalStateException("Device already connected!");
		requestPort = request;
		dataPort = data;
		system.registerOutput(this);
		connected = true;
	}

	@Override
	public short recieve() {
		assertConnected();
		lockedThread = Thread.currentThread();
		system.setIn(requestPort, true);
		LockSupport.park(this);
		return system.getOut(dataPort);
	}
	
	private void assertConnected() {
		if (!connected)
			throw new IllegalStateException("Device is not connected!");
	}

	@Override
	public void disconnect() {
		assertConnected();
		system.unregisterOutput(this);
		connected = false;
		VarHandle.fullFence();
	}
	
	@Override
	public String toString() {
		return "OutputController=" + device.name();
	}

}
