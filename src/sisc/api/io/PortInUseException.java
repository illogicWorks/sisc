package sisc.api.io;

/**
 * Signifies that a port is already in use
 *
 */
@SuppressWarnings("serial")
public class PortInUseException extends RuntimeException {

	public PortInUseException(int port) {
		super(String.valueOf(port));
	}

}
