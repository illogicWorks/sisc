package sisc.api.io;

// todo class docs
public interface InputDevice {
	/**
	 * Gets called when SISC is starting up and should start and connect the device.
	 * If this {@link InputDevice} was declared statically (and therefore via ServiceLoader or
	 * the module system), this method will be called in a just started thread.<p>
	 * 
	 * It is expected for this device to connect to the computer via the {@link InputPair#connectTo(int, int, int)}
	 * in this call.
	 * 
	 * @param pair The companion {@link InputPair} to interact with the computer's ports
	 */
	void start(InputPair pair);

	/**
	 * Gets called when the system is closing. At this point the device should do its cleanup and
	 * finish execution of its threads, such as the one at {@link #start(InputPair)}, given it's not daemon
	 */
	void onClose();

	/**
	 * @return The name of this device <!--TODO make optional or remove--> 
	 */
	String name();
}
