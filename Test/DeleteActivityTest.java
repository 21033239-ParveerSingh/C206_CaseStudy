package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import GA.CCA_Activity;
import GA.SchoolCCARegistrationSystem;

public class DeleteActivityTest {

	@Test
	public void testDeleteExistingActivity() {
		ArrayList<CCA_Activity> activityList = new ArrayList<>();
		CCA_Activity activity = new CCA_Activity("ActivityToDelete");
		activityList.add(activity);

		boolean isDeleted = SchoolCCARegistrationSystem.performDeleteActivity(activityList, "ActivityToDelete");

		assertTrue(isDeleted);
		assertEquals(0, activityList.size());
	}

	@Test
	public void testDeleteNonExistingActivity() {
		ArrayList<CCA_Activity> activityList = new ArrayList<>();
		CCA_Activity activity = new CCA_Activity("ExistingActivity");
		activityList.add(activity);

		boolean isDeleted = SchoolCCARegistrationSystem.performDeleteActivity(activityList, "NonExistingActivity");

		assertFalse(isDeleted);
		assertEquals(1, activityList.size());
	}
}
