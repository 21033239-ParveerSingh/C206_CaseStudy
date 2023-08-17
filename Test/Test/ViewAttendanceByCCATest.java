package Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import GA.AttendanceEntry;
import GA.CCA_Activity;
import GA.SchoolCCARegistrationSystem;

public class ViewAttendanceByCCATest {

	@Test
	public void testViewAttendanceForExistingCCAWithEntries() {
		ArrayList<CCA_Activity> activityList = new ArrayList<>();
		
		CCA_Activity cca1 = new CCA_Activity("Chess Club");
		cca1.addAttendanceEntry(new AttendanceEntry("Alice", LocalDate.of(2023, 8, 15)));
		cca1.addAttendanceEntry(new AttendanceEntry("Bob", LocalDate.of(2023, 8, 16)));
		activityList.add(cca1);

		assertDoesNotThrow(() -> SchoolCCARegistrationSystem.viewAttendanceByCCA(activityList));
	}

	@Test
	public void testViewAttendanceForCCAWithNoEntries() {
		ArrayList<CCA_Activity> activityList = new ArrayList<>();
		// Create and add CCA_Activity objects without attendance entries
		CCA_Activity cca2 = new CCA_Activity("Football Club");
		activityList.add(cca2);

		assertDoesNotThrow(() -> SchoolCCARegistrationSystem.viewAttendanceByCCA(activityList));
	}

	@Test
    public void testViewAttendanceForNonExistentCCA() {
        ArrayList<CCA_Activity> activityList = new ArrayList<>();
        // Create and add CCA_Activity objects with different names
        CCA_Activity cca3 = new CCA_Activity("Music Club");
        activityList.add(cca3);

        assertDoesNotThrow(() -> SchoolCCARegistrationSystem.viewAttendanceByCCA(activityList));
    }
}