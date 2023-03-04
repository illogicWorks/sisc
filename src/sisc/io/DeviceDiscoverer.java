package sisc.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import sisc.api.io.InputDevice;
import sisc.api.io.OutputDevice;

public class DeviceDiscoverer {
	public static List<Object> discoverDevices(IOSystem io) {
		List<Object> devices = new ArrayList<>();
		List<DeviceThread> threadsToStart = new ArrayList<>();
		for (var candidate : ServiceLoader.load(InputDevice.class)) {
			devices.add(handle(candidate, new InputDeviceController(io, candidate), threadsToStart));
		}
		for (var candidate : ServiceLoader.load(OutputDevice.class)) {
			devices.add(handle(candidate, new OutputDeviceController(io, candidate), threadsToStart));
		}
		Collections.shuffle(threadsToStart);
		threadsToStart.forEach(Thread::start);
		return List.copyOf(devices);
	}

	private static <T extends Device<R>, R> R handle(T candidate, R controller, List<DeviceThread> threadsToStart) {
		DeviceThread thread = new DeviceThread(() -> candidate.start(controller));
		threadsToStart.add(thread);
		candidate.configure(thread);
		thread.setName("DeviceThread[" + candidate.name() + "]");
		return controller;
	}
}
