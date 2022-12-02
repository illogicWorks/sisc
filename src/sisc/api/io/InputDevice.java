package sisc.api.io;

// todo class docs
public interface InputDevice {
	/**
	 * Gets called when SISC is starting up and this device should start and connect to the computer.
	 * If this {@link InputDevice} was declared statically (and therefore via ServiceLoader or
	 * the module system), this method will be called in a just started daemon thread.<p>
	 * 
	 * It is expected for this device to connect to the computer via the {@link InputPair#connectTo(int, int, int)}
	 * in this call.
	 * 
	 * @param pair The companion {@link InputPair} to interact with the computer's ports
	 */
	void start(InputPair pair);

	/**
	 * Gets called when the system is closing. At this point the device should do its cleanup and
	 * finish execution of its threads, such as the one started at {@link #start(InputPair)}
	 */
	void onClose();

	/**
	 * @return The name of this device <!--TODO make optional or remove--> 
	 */
	String name();
	
	/**
	 * Gets passed the not-yet started thread to allow this device to configure it,
	 * like setting it to be a daemon thread to simplify closing. Note that name will be
	 * replaced after this call.<p>
	 * 
	 * Does nothing in this default implementation.
	 * 
	 * @param t The thread that will run this device's {@link #start(InputPair)} method
	 */
	default void configure(Thread t) {};
}
