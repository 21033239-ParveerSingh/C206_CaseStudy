package Test;

import org.junit.jupiter.api.Test;
import GA.SchoolCCARegistrationSystem;
import GA.User;
import GA.Student;
import GA.Teacher;
import GA.Administrator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteUserTest {

	@Test
	public void testRemoveUserAccount() {
		ArrayList<User> userList = new ArrayList<>();
		userList.add(new Student("student1"));
		userList.add(new Teacher("teacher1"));
		userList.add(new Administrator("admin1"));

		SchoolCCARegistrationSystem.removeUserAccount(userList, "student1");
		assertEquals(2, userList.size());
		SchoolCCARegistrationSystem.removeUserAccount(userList, "nonexistentUser");
		assertEquals(2, userList.size());
		assertNull(getUserByUsername(userList, "student1"));
	}

	private User getUserByUsername(ArrayList<User> userList, String username) {
		for (User user : userList) {
			if (user.getUsername().equalsIgnoreCase(username)) {
				return user;
			}
		}
		return null;
	}
}
