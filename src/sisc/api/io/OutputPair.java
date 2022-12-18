package sisc.api.io;

import sisc.io.OutputDeviceController;

/**
 * A helper class to talk back to the I/O API from an {@link outputDevice}.
 * Before you can start communicating with the device, you must connect to the computer
 * by calling the {@link #connectTo(int, int, int)} method.
 * 
 * While your device is not connected, attempting to call the methods that modify output
 * ports will throw {@link IllegalStateException}.
 *
 */
public sealed interface OutputPair permits OutputDeviceController {

	/**
	 * Connects the {@link outputDevice} to the computer using the requested ports.<p>
	 * 
	 * You can disconnect by calling {@link #disconnect()}.
	 * 
	 * @param request     The output port the device will flip when offering data. See {@link #recieve()}
	 * @param acknowledge The output port where acknowledge will be signaled. It is useless
	 * @param data        The output port where the computer will read data from. See {@link #recieve()}
	 * 
	 * @throws PortInUseException If some of the requested ports are already in use
	 * @throws IllegalStateException If already connected
	 */
	void connectTo(int request, int acknowledge, int data) throws PortInUseException;

	// Todo docs
	short recieve();
	
	/**
	 * Disconnects the device from the computer.<p>
	 * You can connect back by calling {@link #connectTo(int, int, int)} again.
	 */
	void disconnect();

}
