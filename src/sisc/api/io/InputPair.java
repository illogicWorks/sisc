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
	 * @param request     The output port where the computer will flip its request bit. See {@link InputDevice#onReqUp()} and {@link #reqStatus()}
	 * @param acknowledge The input port where acknowledge will be signaled. See {@link #acknowledge(short)}
	 * @param data        The input port where the computer will read data from. See {@link #acknowledge(short)}
	 * 
	 * @throws PortInUseException If some of the requested ports are already in use
	 * @throws IllegalStateException If already connected
	 */
	void connectTo(int request, int acknowledge, int data) throws PortInUseException;

	/**
	 * Flips the acknowledge bus to one and the data bus to the given data.<p>
	 * Setting it back to low is already handled implicitly.
	 * @param The data to put in the data port
	 */
	void acknowledge(short data);

	/**
	 * @return The current status of the request port
	 * @see InputDevice#onReqUp()
	 */
	boolean reqStatus();
	
	/**
	 * Disconnects the device from the computer.<p>
	 * You can connect back by calling {@link #connectTo(int, int, int)} again.
	 */
	void disconnect();
}
