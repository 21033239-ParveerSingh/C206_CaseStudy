package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GA.CCA_Activity;
import GA.SchoolCCARegistrationSystem;
import GA.TimeSlot;

public class ViewAllTimeSlotsTest {

	private final PrintStream originalOut = System.out;
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	public void setUpStreams() {
		System.setOut(new PrintStream(outputStream));
	}

	@AfterEach
	public void restoreStreams() {
		System.setOut(originalOut);
	}

	@Test
	public void testViewAllTimeSlotsNoSlotsAvailable() {
		CCA_Activity activity = new CCA_Activity("Activity1");
		ArrayList<CCA_Activity> activityList = new ArrayList<>();
		activityList.add(activity);

		SchoolCCARegistrationSystem.viewAllTimeSlots(activityList);

		String expectedOutput = "--------------------------------------------------------------------------------\r\n"
				+ "View All Time Slots\r\n"
				+ "--------------------------------------------------------------------------------\nTime Slots for Activity1:\nNo time slots available.\n";
		assertEquals(expectedOutput, outputStream.toString());
	}

	@Test
	public void testViewAllTimeSlotsWithSlots() {
		CCA_Activity activity = new CCA_Activity("Activity1");
		TimeSlot timeSlot1 = new TimeSlot("08:00", "10:00");
		TimeSlot timeSlot2 = new TimeSlot("14:00", "16:00");
		activity.addTimeSlot(timeSlot1);
		activity.addTimeSlot(timeSlot2);
		ArrayList<CCA_Activity> activityList = new ArrayList<>();
		activityList.add(activity);

		SchoolCCARegistrationSystem.viewAllTimeSlots(activityList);

		String expectedOutput = "--------------------------------------------------------------------------------\r\n"
				+ "View All Time Slots\r\n"
				+ "--------------------------------------------------------------------------------\nTime Slots for Activity1:\nStart Time: 08:00, End Time: 10:00\nStart Time: 14:00, End Time: 16:00\n";
		assertEquals(expectedOutput, outputStream.toString());
	}
}
