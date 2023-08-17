package GA;

import java.util.ArrayList;
import java.util.List;

public class UserDeletion {
	private List<Student> students;
	private List<Administrator> administrators;

	public UserDeletion() {
		students = new ArrayList<>();
		administrators = new ArrayList<>();
	}

	public boolean deleteUser(Student student) {
		if (students.contains(student)) {
			students.remove(student);
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteUser(Administrator administrator) {
		if (administrators.contains(administrator)) {
			administrators.remove(administrator);
			return true;
		} else {
			return false;
		}
	}
}
