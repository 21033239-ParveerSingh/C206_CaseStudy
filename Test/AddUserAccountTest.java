package Test;

import org.junit.jupiter.api.Test;
import GA.User;
import GA.Student;
import GA.Teacher;
import GA.Administrator;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddUserAccountTest {

	@Test
	public void testAddUserAccount() {
		ArrayList<User> userList = new ArrayList<>();

		userList.add(new Student("alice"));
		assertEquals(1, userList.size());
		assertTrue(userList.get(0) instanceof Student);

		userList.add(new Teacher("bob"));
		assertEquals(2, userList.size());
		assertTrue(userList.get(1) instanceof Teacher);

		userList.add(new Administrator("admin"));
		assertEquals(3, userList.size());
		assertTrue(userList.get(2) instanceof Administrator);
	}
}
