import sisc.ConsoleInputDevice;

/**
 * @author plopez
 * @author altrisi
 *
 */
module sisc {
	exports sisc.api.io;
	provides sisc.api.io.InputDevice with ConsoleInputDevice;
}
