package Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class TestHelper {

	private static InputStream originalSystemIn = System.in;
	private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private static final PrintStream printStream = new PrintStream(outputStream);

	public static void setInput(String input) {
		System.setIn(new ByteArrayInputStream(input.getBytes()));
	}

	public static String getOutput() {
		return outputStream.toString();
	}

	public static void resetStreams() {
		System.setIn(originalSystemIn);
		System.setOut(printStream);
		outputStream.reset();
	}

	public static String captureOutput(Runnable task) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outputStream));

		task.run();

		System.setOut(originalOut);
		return outputStream.toString();
	}
}
