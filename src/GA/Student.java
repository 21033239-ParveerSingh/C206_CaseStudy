package GA;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

	private List<RegisteredActivity> registeredActivities;

	public Student(String username) {
		super(username, "Student");
		registeredActivities = new ArrayList<>();
	}

	public void registerForActivity(CCA_Activity activity, TimeSlot timeSlot) {
		RegisteredActivity newRegisteredActivity = new RegisteredActivity(activity, timeSlot);
		newRegisteredActivity.setApprovalStatus("Pending");
		registeredActivities.add(newRegisteredActivity);
	}

	public List<RegisteredActivity> getRegisteredActivities() {
		return registeredActivities;
	}

	public boolean isRegistered(CCA_Activity activity, TimeSlot timeSlot) {
		for (RegisteredActivity registeredActivity : registeredActivities) {
			if (registeredActivity.getActivity().equals(activity)
					&& registeredActivity.getTimeSlot().equals(timeSlot)) {
				return true;
			}
		}
		return false;
	}

	public boolean deleteRegistration(CCA_Activity activity, TimeSlot timeSlot) {
		RegisteredActivity registrationToRemove = null;
		for (RegisteredActivity registration : registeredActivities) {
			if (registration.getActivity().equals(activity) && registration.getTimeSlot().equals(timeSlot)) {
				registrationToRemove = registration;
				break;
			}
		}

		if (registrationToRemove != null) {
			registeredActivities.remove(registrationToRemove);
			return true;
		}
		return false;
	}
}
