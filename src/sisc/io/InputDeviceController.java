package sisc.io;

import java.util.concurrent.locks.LockSupport;

import sisc.api.io.*;

public final class InputDeviceController implements InputPair, PortReadListener {
	private final InputDevice device;
	private final IOSystem system;
	private boolean connected;
	private volatile Thread lockedThread;
	/* package */ int requestPort;
	              int acknowledgePort;
	              int dataPort;

	public InputDeviceController(IOSystem system, InputDevice device) {
		this.device = device;
		this.system = system;
	}

	@Override
	public void connectTo(int request, int acknowledge, int data) throws PortInUseException {
		if (connected)
			throw new IllegalStateException("Device already connected!");
		this.requestPort = request;
		this.acknowledgePort = acknowledge;
		this.dataPort = data;
		system.registerInput(this);
		this.connected = true;
	}

	@Override
	public void offer(short data) {
		assertConnected();
		if (system.getIn(acknowledgePort) != 0)
			throw new IllegalStateException("Already offering data");
		lockedThread = Thread.currentThread();
		system.setIn(dataPort, data);
		system.setIn(requestPort, true);
		LockSupport.park(this);
	}

	@Override
	public void disconnect() {
		assertConnected();
		system.unregisterInput(this);
		this.connected = false;
	}

	private void assertConnected() {
		if (!connected)
			throw new IllegalStateException("Device is not connected!");
	}

	// called after our data port is read. We implicitly flip acknowledge low and unblock the
	// thread that called into us
	@Override
	public void onPortRead() {
		system.setIn(requestPort, false);
		system.setIn(acknowledgePort, false);
		LockSupport.unpark(lockedThread);
		lockedThread = null;
	}
	
	@Override
	public String toString() {
		return "InputController=" + device.name();
	}
}
