package sisc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class FileReader{
	
	public static List<BitSet> LoadImage(String path) {
		List<BitSet> instructions = new ArrayList<>();
		Path pth = Path.of(path);
		
		System.out.println("Reading from " + pth.toAbsolutePath());
	
		try (InputStream is = Files.newInputStream(pth)){
			// Read two bytes because instructions are 16-bit wide
			byte[] data;
			while((data = is.readNBytes(2)).length != 0) {
				BitSet instr = BitSet.valueOf(data);
				instructions.add(instr);
			}
		} catch (IOException e) {
			//TODO Implement error handling
			e.printStackTrace();
		}
		return instructions;
	}
}
