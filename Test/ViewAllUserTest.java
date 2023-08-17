package Test;

import org.junit.jupiter.api.Test;
import GA.SchoolCCARegistrationSystem;
import GA.User;
import GA.Student;
import GA.Teacher;
import GA.Administrator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewAllUserTest {

	@Test
	public void testViewUserAccounts() {
		ArrayList<User> userList = new ArrayList<>();
		userList.add(new Student("student1"));
		userList.add(new Teacher("teacher1"));
		userList.add(new Administrator("admin1"));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		SchoolCCARegistrationSystem.viewUserAccounts(userList);

		System.setOut(System.out);

		String[] actualLines = outputStream.toString().trim().split("\\r?\\n");

		List<String> expectedLines = Arrays.asList("User Accounts:", "Username: student1, Role: Student",
				"Username: teacher1, Role: Teacher", "Username: admin1, Role: Admin");

		assertEquals(expectedLines.size(), actualLines.length);
		for (int i = 0; i < expectedLines.size(); i++) {
			assertEquals(expectedLines.get(i), actualLines[i]);
		}
	}
}
