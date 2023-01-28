package sisc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader{
	
	public static short[] loadImage(String path) {
		Path pth = Path.of(path);
		short[] instructions;
		try {
			instructions = new short[(int) Files.size(pth)/2];
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
		
		System.out.println("Reading from " + pth.toAbsolutePath());
	
		try (InputStream is = Files.newInputStream(pth)){
			// Read two bytes because instructions are 16-bit wide
			byte[] data;
			for(int i = 0; (data = is.readNBytes(2)).length != 0; i++) {
				instructions[i] = (short) Byte.toUnsignedInt(data[0]);
				instructions[i] |= data[1] << 8;
			}
		} catch (IOException e) {
			throw new RuntimeException("Exception while loading image", e);
		}
		return instructions;
	}

	public static byte[] loadImageBytes(String path) {
		Path pth = Path.of(path);
		System.out.println("Reading from " + pth.toAbsolutePath());

		try (InputStream is = Files.newInputStream(pth)) {
			return is.readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException("Exception while loading image", e);
		}
	}
}
