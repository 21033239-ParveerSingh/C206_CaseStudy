package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import GA.CCA_Activity;
import GA.SchoolCCARegistrationSystem;

public class ViewActivitiesTest {

    @Test
    public void testViewAllActivities() {
        ArrayList<CCA_Activity> activityList = new ArrayList<>();
        CCA_Activity activity1 = new CCA_Activity("Activity 1");
        activityList.add(activity1);

        String expectedOutput = "CCA NAMES:\n\nActivity 1\n\n";

        String actualOutput = TestHelper.captureOutput(() -> SchoolCCARegistrationSystem.viewAllActivities(activityList));

        assertEquals(expectedOutput, actualOutput);
    }
}

