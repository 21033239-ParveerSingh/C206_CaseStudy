package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GA.CCA_Activity;
import GA.SchoolCCARegistrationSystem;

public class ViewAllTimeSlotsTest {
	private final InputStream originalSystemIn = System.in;
	private final PrintStream originalSystemOut = System.out;
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	public void setUpStreams() {
		System.setOut(new PrintStream(outputStream));
	}

	@AfterEach
	public void restoreStreams() {
		System.setIn(originalSystemIn);
		System.setOut(originalSystemOut);
	}

	@Test
	public void testViewAllTimeSlotsWithEmptyActivityList() {
		ArrayList<CCA_Activity> activityList = new ArrayList<>();
		TestHelper.captureOutput(() -> SchoolCCARegistrationSystem.viewAllTimeSlots(activityList));

		String expectedOutput = "";
		assertEquals(expectedOutput.trim(), outputStream.toString().trim());
	}

	@Test
	public void testViewAllTimeSlotsWithNonEmptyActivityList() {
		ArrayList<CCA_Activity> activityList = new ArrayList<>();

		TestHelper.captureOutput(() -> SchoolCCARegistrationSystem.viewAllTimeSlots(activityList));

		String expectedOutput = "";
		assertEquals(expectedOutput.trim(), outputStream.toString().trim());
	}
}
