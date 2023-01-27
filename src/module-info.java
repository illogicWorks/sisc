import sisc.api.io.InputDevice;
import sisc.api.io.OutputDevice;
import sisc.devices.TerminalInput;
import sisc.devices.TerminalNumberOutput;

/**
 * @author plopez
 * @author altrisi
 *
 */
module sisc {
	uses InputDevice;
	uses OutputDevice;

	exports sisc.api.io;
	
	provides OutputDevice with TerminalNumberOutput;
	provides InputDevice with TerminalInput;
}
