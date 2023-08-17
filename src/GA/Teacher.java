package GA;

import java.util.List;

public class Teacher {
	private String id;
	private String name;

	public Teacher(String id, String name) {
		this.setId(id);
		this.setName(name);
	}

	public void viewAllRegistrations(RegistrationController registrationController) {
		List<Registration> registrations = registrationController.viewAllRegistrations();

		System.out.println("Registrations:");
		for (Registration registration : registrations) {
			Student student = registration.getStudent();
			Activity activity = registration.getActivity();

			System.out.println("Student: " + student.getName());
			System.out.println("Activity: " + activity.getName());
			System.out.println("Date: " + activity.getDate());
			System.out.println("Time: " + activity.getTime());

			System.out.println("----------------------");
		}
	}

	public boolean deleteExistingRegistration(RegistrationController controller, Student student, Activity activity) {
		// Retrieve the list of registrations from the controller
		List<Registration> registrations = controller.viewAllRegistrations();

		// Find the registration to delete
		Registration registrationToDelete = null;
		for (Registration reg : registrations) {
			if (reg.getStudent().equals(student) && reg.getActivity().equals(activity)) {
				registrationToDelete = reg;
				break;
			}
		}

		// If the registration was found, remove it and return true
		if (registrationToDelete != null) {
			registrations.remove(registrationToDelete);
			return true;
		}

		// If the registration was not found, return false
		return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
