package sisc.api.io;

import sisc.io.InputDeviceController;

/**
 * A helper class to talk back to the I/O API from an {@link InputDevice}.
 * Before you can start communicating with the device, you must connect to the computer
 * by calling the {@link #connectTo(int, int, int)} method.
 * 
 * While your device is not connected, attempting to call the methods that modify input
 * ports will throw {@link IllegalStateException}.
 *
 */
public sealed interface InputPair permits InputDeviceController {
	/**
	 * Connects the {@link InputDevice} to the computer using the requested ports.<p>
	 * 
	 * You can disconnect by calling {@link #disconnect()}.
	 * 
	 * @param request     The input port the device will flip when offering data. See {@link #offer(short)}
	 * @param acknowledge The output port where acknowledge will be signaled. It is useless
	 * @param data        The input port where the computer will read data from. See {@link #offer(short)}
	 * 
	 * @throws PortInUseException If some of the requested ports are already in use
	 * @throws IllegalStateException If already connected
	 */
	void connectTo(int request, int acknowledge, int data) throws PortInUseException;

	/**
	 * Sets the data bus to the given data and the request bus to one.<p>
	 * The calling thread will be locked until the computer reads the data.
	 * @param data The data to put in the data port
	 */
	void offer(short data);
	
	/**
	 * Disconnects the device from the computer.<p>
	 * You can connect back by calling {@link #connectTo(int, int, int)} again.
	 */
	void disconnect();
}
