package sisc;

import java.io.IOException;
import java.io.InputStreamReader;

import sisc.api.io.InputDevice;
import sisc.api.io.InputPair;

public class ConsoleInputDevice implements InputDevice {
	private boolean closed;
	@Override
	public void start(InputPair pair) {
		pair.connectTo(0, 1, 1);
		InputStreamReader reader = new InputStreamReader(System.in);
		char[] buff = new char[1];
		while (!closed) {
			try {
				reader.read(buff);
				pair.offer((short)buff[0]);
			} catch (IOException e) {
				throw new AssertionError();
			}
		}
	}

	@Override
	public void onClose() {
		// haha yes. This doesn't work because reader.read waits indefinitely for input.
		// would need to do some kind-of active wait, or just set ourselves as daemon somehow
		// edit: done this is now a daemon thread
		closed = true;
	}

	@Override
	public String name() {
		return "Console";
	}
	
	@Override
	public void configure(Thread t) {
		t.setDaemon(true);
	}
}
