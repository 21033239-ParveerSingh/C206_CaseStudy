package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GA.CCA_Activity;
import GA.RegisteredActivity;
import GA.SchoolCCARegistrationSystem;
import GA.Student;
import GA.TimeSlot;
import GA.User;

public class ApproveOrRejectApplicationsTest {

	private ArrayList<User> userList;
	private Student student;
	private RegisteredActivity registeredActivity;

	@BeforeEach
	public void setUp() {
		userList = new ArrayList<>();

		student = new Student("John");
		CCA_Activity volleyballActivity = new CCA_Activity("Volleyball");
		TimeSlot volleyballTimeSlot = new TimeSlot("10:00", "11:00");
		volleyballActivity.addTimeSlot(volleyballTimeSlot);

		student.registerForActivity(volleyballActivity, volleyballTimeSlot);
		registeredActivity = student.getRegisteredActivities().get(0);
	}

	@Test
	public void testApproveStudentRegistration() {

		SchoolCCARegistrationSystem.approveStudentRegistration(student, registeredActivity);
		assertEquals("Approved", registeredActivity.getApprovalStatus());
	}

	@Test
	public void testRejectStudentRegistration() {

		SchoolCCARegistrationSystem.rejectStudentRegistration(student, registeredActivity);
		assertEquals("Rejected", registeredActivity.getApprovalStatus());
	}
}
