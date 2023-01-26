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
			InputDeviceController controller = new InputDeviceController(io, candidate);
			devices.add(controller);
			DeviceThread thread = new DeviceThread(() -> candidate.start(controller));
			threadsToStart.add(thread);
			candidate.configure(thread);
			thread.setName("InputDeviceThread[" + candidate.name() + "]");
		}
		for (var candidate : ServiceLoader.load(OutputDevice.class)) {
			OutputDeviceController controller = new OutputDeviceController(io, candidate);
			devices.add(controller);
			DeviceThread thread = new DeviceThread(() -> candidate.start(controller));
			threadsToStart.add(thread);
			candidate.configure(thread);
			thread.setName("OutputDeviceThread[" + candidate.name() + "]");
		}
		Collections.shuffle(threadsToStart);
		threadsToStart.forEach(Thread::start);
		return List.copyOf(devices);
	}
}
