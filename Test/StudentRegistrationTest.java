package Test;

import GA.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StudentRegistrationTest {

    @Test
    public void testStudentAddRegistration() {
        // Create a student
        Student student = new Student("student1");

        // Create an activity
        CCA_Activity activity = new CCA_Activity("Activity1");

        // Create a time slot
        TimeSlot timeSlot = new TimeSlot("09:00", "11:00");

        // Student adds a registration
        student.registerForActivity(activity, timeSlot);

        // Perform assertions
        assertEquals(1, student.getRegisteredActivities().size());
        RegisteredActivity addedRegistration = student.getRegisteredActivities().get(0);
        assertEquals(activity, addedRegistration.getActivity());
        assertEquals(timeSlot, addedRegistration.getTimeSlot());
    }

    @Test
    public void testStudentDeleteRegistration() {
        // Create a student
        Student student = new Student("student1");

        // Create an activity
        CCA_Activity activity = new CCA_Activity("Activity1");

        // Create a time slot
        TimeSlot timeSlot = new TimeSlot("09:00", "11:00");

        // Student adds a registration
        student.registerForActivity(activity, timeSlot);

        // Student deletes the registration
        boolean isDeleted = student.deleteRegistration(activity, timeSlot);

        // Perform assertions
        assertEquals(true, isDeleted);
        assertEquals(0, student.getRegisteredActivities().size());
    }

    @Test
    public void testTeacherViewExistingRegistrations() {
        // Create an activity
        CCA_Activity activity = new CCA_Activity("Activity1");

        // Create a time slot
        TimeSlot timeSlot = new TimeSlot("09:00", "11:00");

        // Register a student for the activity
        Student student = new Student("student1");
        student.registerForActivity(activity, timeSlot);

        // Teacher views existing registrations
        StringBuilder registrationDetails = new StringBuilder();
        for (RegisteredActivity reg : student.getRegisteredActivities()) {
            registrationDetails.append("Student: ").append(student.getUsername()).append(", Activity: ").append(reg.getActivity().getName()).append(", Time Slot: ").append(reg.getTimeSlot()).append("\n");
        }

        // Perform assertions
        String expectedDetails = "Student: student1, Activity: Activity1, Time Slot: 09:00 - 11:00\n";
        assertEquals(expectedDetails, registrationDetails.toString());
    }
}
