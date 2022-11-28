package sisc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {
	public void LoadImage(String path) {
		System.out.println("Reading from " + path);
		try {
			Files.readAllBytes(Path.of(path));
		} catch (IOException e) {
			//TODO Implement error handling
			e.printStackTrace();
		}
	}
}
