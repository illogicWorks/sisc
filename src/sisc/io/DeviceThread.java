package sisc.io;

// marker class to distinguish threads
public final class DeviceThread extends Thread {
	public DeviceThread(Runnable task) {
		super(task);
	}
}
