package GA;

import java.util.ArrayList;
import java.util.List;

public class UserViewing {
	private List<Student> students;
	private List<Teacher> teachers;
	private List<Administrator> administrators;

	public UserViewing() {
		students = new ArrayList<>();
		teachers = new ArrayList<>();
		administrators = new ArrayList<>();
	}

	public void addStudent(Student student) {
		students.add(student);
	}

	public void addTeacher(Teacher teacher) {
		teachers.add(teacher);
	}

	public void addAdministrator(Administrator administrator) {
		administrators.add(administrator);
	}

	public List<Student> getAllStudents() {
		return new ArrayList<>(students);
	}

	public List<Teacher> getAllTeachers() {
		return new ArrayList<>(teachers);
	}

	public List<Administrator> getAllAdministrators() {
		return new ArrayList<>(administrators);
	}

	public List<Student> viewStudentsOnly() {
		return new ArrayList<>(students);
	}

	public List<Teacher> viewTeachersOnly() {
		return new ArrayList<>(teachers);
	}

	public List<Administrator> viewAdministratorsOnly() {
		return new ArrayList<>(administrators);
	}
//    public List<? extends users> getAllUsers() {
//        List<users> allUsers = new ArrayList<>();
//        allUsers.addAll(students);
//        allUsers.addAll(teachers);
//        allUsers.addAll(administrators);
//        return allUsers;
//    }

}
