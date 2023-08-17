package Test;

import GA.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StudentRegistrationTest {

	@Test
	public void testStudentAddRegistration() {

		Student student = new Student("student1");

		CCA_Activity activity = new CCA_Activity("Activity1");

		TimeSlot timeSlot = new TimeSlot("09:00", "11:00");

		student.registerForActivity(activity, timeSlot);

		assertEquals(1, student.getRegisteredActivities().size());
		RegisteredActivity addedRegistration = student.getRegisteredActivities().get(0);
		assertEquals(activity, addedRegistration.getActivity());
		assertEquals(timeSlot, addedRegistration.getTimeSlot());
	}

	@Test
	public void testStudentDeleteRegistration() {
		
		Student student = new Student("student1");

		CCA_Activity activity = new CCA_Activity("Activity1");

		TimeSlot timeSlot = new TimeSlot("09:00", "11:00");

		student.registerForActivity(activity, timeSlot);

		boolean isDeleted = student.deleteRegistration(activity, timeSlot);

		assertEquals(true, isDeleted);
		assertEquals(0, student.getRegisteredActivities().size());
	}

	@Test
	public void testTeacherViewExistingRegistrations() {

		CCA_Activity activity = new CCA_Activity("Activity1");

		TimeSlot timeSlot = new TimeSlot("09:00", "11:00");

		Student student = new Student("student1");
		student.registerForActivity(activity, timeSlot);

		StringBuilder registrationDetails = new StringBuilder();
		for (RegisteredActivity reg : student.getRegisteredActivities()) {
			registrationDetails.append("Student: ").append(student.getUsername()).append(", Activity: ")
					.append(reg.getActivity().getName()).append(", Time Slot: ").append(reg.getTimeSlot()).append("\n");
		}

		String expectedDetails = "Student: student1, Activity: Activity1, Time Slot: 09:00 - 11:00\n";
		assertEquals(expectedDetails, registrationDetails.toString());
	}
}
