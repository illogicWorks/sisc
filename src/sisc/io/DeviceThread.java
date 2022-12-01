package sisc.io;

public final class DeviceThread extends Thread {
	public DeviceThread(String name, Runnable task) {
		super(task);
		setName(name);
	}
}
