package GA;

import java.util.ArrayList;
import java.util.List;

public class RegistrationController {
	private List<Registration> registration;

	public RegistrationController() {
		registration = new ArrayList<>();
	}

	public boolean addNewRegistration(Student student, Activity activity) {
		Registration newRegistration = new Registration(student, activity);
		if (!registration.contains(newRegistration)) {
			registration.add(newRegistration);
			return true;
		}
		return false;
	}

	public List<Registration> viewAllRegistrations() {
		return new ArrayList<>(registration);
	}

	public boolean deleteExistingRegistration(Student student, Activity activity) {
		Registration registrationToRemove = new Registration(student, activity);
		return registration.remove(registrationToRemove);
	}
}
