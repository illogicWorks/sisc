package sisc.io;

import sisc.api.io.*;

public sealed interface Device<T> permits InputDevice, OutputDevice {
	void start(T pair);
	String name();
	void configure(Thread t);
	void onClose();
}
