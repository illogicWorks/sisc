package sisc.devices;

import sisc.api.io.OutputDevice;
import sisc.api.io.OutputPair;

public class TerminalNumberOutput implements OutputDevice {

	@Override
	public void start(OutputPair pair) {
		// TODO Auto-generated method stub
		pair.connectTo(0, 50, 1);
		while (true) {
			System.out.println(pair.recieve());
		}
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "Terminal";
	}
 
}
