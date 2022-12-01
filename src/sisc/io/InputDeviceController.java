package sisc.io;

import java.lang.invoke.VarHandle;

import sisc.api.io.*;

public final class InputDeviceController implements InputPair, PortChangeListener, PortReadListener {
	private final InputDevice device;
	private final IOSystem system;
	private boolean connected;
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
		VarHandle.fullFence();
	}

	@Override
	public void acknowledge(short data) {
		assertConnected();
		system.setIn(acknowledgePort, true);
		system.setIn(dataPort, data);
	}

	@Override
	public boolean reqStatus() {
		assertConnected();
		return system.getOut(requestPort) == 1;
	}

	@Override
	public void disconnect() {
		assertConnected();
		system.unregisterInput(this);
		this.connected = false;
		VarHandle.fullFence();
	}

	private void assertConnected() {
		if (!connected)
			throw new IllegalStateException("Device is not connected!");
	}

	// called after our data port is read. We implicitly flip acknowledge low
	@Override
	public void onPortRead() {
		system.setIn(acknowledgePort, false);
	}

	// called when the request port is changed
	@Override
	public void onPortChanged(short value) {
		if (value == 1)
			device.onReqUp();
	}
	
	@Override
	public String toString() {
		return "InputController=" + device.name();
	}
}
