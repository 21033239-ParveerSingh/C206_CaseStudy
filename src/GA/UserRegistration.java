package GA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRegistration {
	private List<Student> students;
	private Set<String> sentEmails;

	public UserRegistration() {
		students = new ArrayList<>();
		sentEmails = new HashSet<>();
	}

	public boolean RegisterUser(String id, String name, String email, String password) {
		if (isEmailUnique(email)) {
			Student newStudent = new Student(id, name, email, password);
			students.add(newStudent);
			sendConfirmationEmail(email);
			return true;
		} else {
			return false;
		}
	}

	public boolean isEmailSent(String email) {
		return sentEmails.contains(email);
	}

	private boolean isEmailUnique(String email) {
		for (Student student : students) {
			if (student.getEmail().equals(email)) {
				return false;
			}
		}
		return true;
	}

	private void sendConfirmationEmail(String email) {
		// Implement your email sending logic here
		// For simplicity, you can just add the email to the sentEmails set
		sentEmails.add(email);
		System.out.println("Confirmation email sent to: " + email);
	}
}