package sisc.io;

import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.LockSupport;

import sisc.api.io.OutputDevice;
import sisc.api.io.OutputPair;
import sisc.api.io.PortInUseException;

public final class OutputDeviceController implements OutputPair, PortChangeListener {
	private final OutputDevice device;
	private final IOSystem system;
	private boolean connected;
	private volatile Thread lockedThread;
	
	int requestPort;
	int acknowledgePort;
	int dataPort;
	
	public OutputDeviceController(IOSystem system, OutputDevice device) {
		this.device = device;
		this.system = system;
	}
	
	@Override
	public void onPortChanged(short value) {
		//TODO: implement 
		
	}

	@Override
	public void connectTo(int request, int acknowledge, int data) throws PortInUseException {
		if (connected)
			throw new IllegalStateException("Device already connected!");
		this.requestPort = request;
		this.acknowledgePort = acknowledge;
		this.dataPort = data;
		//TODO REGISTER OUTPUT 
		this.connected = true;
		VarHandle.fullFence();
	}

	@Override
	public short recieve() {
		assertConnected();
		//TODO: get the output
		
		lockedThread = Thread.currentThread();
		LockSupport.park(this);
	}
	
	private void assertConnected() {
		if (!connected)
			throw new IllegalStateException("Device is not connected!");
	}

	@Override
	public void disconnect() {
		assertConnected();
		// TODO: Implement unregister output
		this.connected = false;
		VarHandle.fullFence();
	}

}
