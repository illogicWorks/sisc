package sisc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FileReader {
	public void LoadImage(String path) {
		System.out.println("Reading from " + path);
		Path pth = Path.of(path);
		System.out.println(pth.toAbsolutePath());
		try {
			byte[] data = Files.readAllBytes(pth);
			System.out.println(Arrays.toString(data));
		} catch (IOException e) {
			//TODO Implement error handling
			e.printStackTrace();
		}
	}
}
