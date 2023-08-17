package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import GA.CCA_Activity;
import GA.SchoolCCARegistrationSystem;

public class AddActivityTest {

	@Test
	public void testAddActivity() {
		ArrayList<CCA_Activity> activityList = new ArrayList<>();
		String ccaName = "New Activity";

		SchoolCCARegistrationSystem.addActivity(activityList);

		assertEquals(1, activityList.size());
		assertEquals(ccaName, activityList.get(0).getName());
	}

	@Test
	public void testAddExistingActivity() {
		ArrayList<CCA_Activity> activityList = new ArrayList<>();
		CCA_Activity existingActivity = new CCA_Activity("Existing Activity");
		activityList.add(existingActivity);

		String ccaName = "Existing Activity";
		String expectedOutput = "That activity already exists!\n";
		String actualOutput = TestHelper.captureOutput(() -> SchoolCCARegistrationSystem.addActivity(activityList));

		assertEquals(1, activityList.size());
		assertEquals(expectedOutput, actualOutput);
	}
}
