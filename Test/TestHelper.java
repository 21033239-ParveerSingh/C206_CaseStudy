package Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestHelper {
	
	public static String captureOutput(Runnable task) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outputStream));

		task.run();

		System.setOut(originalOut);
		return outputStream.toString();
	}
}
