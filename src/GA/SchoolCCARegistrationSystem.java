package GA;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchoolCCARegistrationSystem {

	private static ArrayList<User> userList = new ArrayList<User>();

	// Remember to add preset values for each CRUD
	public static void main(String[] args) {

		ArrayList<CCA_Activity> activityList = new ArrayList<CCA_Activity>();

		// Preset activities
		CCA_Activity volleyballActivity = new CCA_Activity("Volleyball");
		TimeSlot volleyballTimeSlot = new TimeSlot("10:00", "11:00");
		volleyballActivity.addTimeSlot(volleyballTimeSlot);
		activityList.add(volleyballActivity);

		// Preset User Accounts
		userList.add(new Student("alice"));
		userList.add(new Teacher("bob"));
		userList.add(new Administrator("admin"));

		// Welcome message
		String welcome = "";
		Helper.line(80, "-");
		welcome += "Welcome to the School CCA Registration System!";
		System.out.println(welcome);
		Helper.line(80, "-");

		boolean isLoggedIn = false;
		User user = null;

		while (true) {
			int choice = loginOrClose();

			if (choice == 1) {
				user = login();

				if (user != null) {
					isLoggedIn = true;

				} else {
					System.out.println("Login failed. Please try again.");
				}

			} else if (choice == 2) {
				signup();

			} else if (choice == 3) {
				System.out.println("\nClosing the application...");
				break;

			} else {
				System.out.println("Invalid choice. Please choose again.");
			}

			if (isLoggedIn) {
				System.out.println("Logged in as " + user.getUsername() + " (" + user.getRole() + ")");

				if (user instanceof Student) {
					handleStudentOption((Student) user, activityList, userList);
				} else if (user instanceof Teacher) {
					handleTeacherOption((Teacher) user, activityList, userList);
				} else if (user instanceof Administrator) {
					handleAdminOption((Administrator) user, activityList, userList);
				}

				// Reset login status
				isLoggedIn = false;
				user = null;
			}
		}

		System.out.println("\nThank you for using the School CCA Registration System. Have a nice Day!\n");
	}

	// ===================== Activities =====================

	// Option 1 View activities (CRUD- Read)
	public static void viewAllActivities(ArrayList<CCA_Activity> activityList) {
		String output = String.format("CCA NAMES:\n\n");
		output += retrieveAllActivities(activityList);
		System.out.println(output);
	}

	// Option 2 Add new activity (CRUD - Create)
	public static void addActivity(ArrayList<CCA_Activity> activityList, String ccaName) {

		for (CCA_Activity activityName : activityList) {
			if (ccaName.equalsIgnoreCase(activityName.getName())) {
				System.out.println("That activity already exists!");
				return;
			}
		}

		CCA_Activity newActivity = new CCA_Activity(ccaName);
		activityList.add(newActivity);
		System.out.println("Activity '" + ccaName + "' has been added.");
	}

	// Option 3 Delete an Activity (CRUD - Delete)
	public static void deleteActivity(ArrayList<CCA_Activity> activityList) {
		Helper.line(80, "-");
		System.out.println("Delete a CCA Activity");
		viewAllActivities(activityList);
		Helper.line(80, "-");
		String ccaName = Helper.readString("Enter the name of the activity to delete: ");
		Helper.line(80, "-");
		boolean isDeleted = performDeleteActivity(activityList, ccaName);
		if (isDeleted) {
			System.out.println("Activity '" + ccaName + "' has been deleted.");
		} else {
			System.out.println("Activity '" + ccaName + "' not found or couldn't be deleted.");
		}
	}

	// ===================== Time Slots =====================

	public static void viewAllTimeSlots(ArrayList<CCA_Activity> activityList) {
		setHeader("View All Time Slots");
		for (CCA_Activity activityName : activityList) {
			System.out.println("Time Slots for " + activityName.getName() + ":");
			List<TimeSlot> timeSlots = activityName.getTimeSlots();
			if (timeSlots.isEmpty()) {
				System.out.println("No time slots available.");
			} else {
				for (TimeSlot timeSlot : timeSlots) {
					System.out
							.println("Start Time: " + timeSlot.getStartTime() + ", End Time: " + timeSlot.getEndTime());
				}
			}
		}
	}

	public static void addTimeSlots(ArrayList<CCA_Activity> activityList) {
		setHeader("Manage Time Slots");
		viewAllActivities(activityList);
		Helper.line(80, "-");
		String ccaName = Helper.readString("Enter the name of the activity to add a time slot for: ");
		CCA_Activity selectedActivity = findActivityByName(activityList, ccaName);

		if (selectedActivity != null) {
			setHeader("Add a time slot for " + selectedActivity.getName());

			// Add a new time slot
			String startTime = Helper.readString("Enter start time (HH:mm): ");
			String endTime = Helper.readString("Enter end time (HH:mm): ");

			if (validateTimeFormat(startTime) && validateTimeFormat(endTime)) {
				TimeSlot newTimeSlot = new TimeSlot(startTime, endTime);

				boolean conflicts = false;
				boolean exactMatch = false;

				for (TimeSlot existingTimeSlot : selectedActivity.getTimeSlots()) {
					if (isTimeConflict(existingTimeSlot, newTimeSlot)) {
						conflicts = true;
						System.out.println("A conflicting time slot already exists for " + selectedActivity.getName());
						break;
					}
					if (existingTimeSlot.getStartTime().equals(newTimeSlot.getStartTime())
							&& existingTimeSlot.getEndTime().equals(newTimeSlot.getEndTime())) {
						exactMatch = true;
						System.out.println(
								"An exact matching time slot already exists for " + selectedActivity.getName());
						break;
					}
				}

				if (!conflicts && !exactMatch) {
					selectedActivity.addTimeSlot(newTimeSlot);
					System.out.println("Time slot " + startTime + " to " + endTime + " added for "
							+ selectedActivity.getName() + " successfully.");
				}
			} else {
				System.out.println("Invalid time format. Please use HH:mm format.");
			}
		} else {
			System.out.println("Activity '" + ccaName + "' not found.");
		}
	}

	public static void deleteTimeSlot(ArrayList<CCA_Activity> activityList) {
		setHeader("Delete Time Slot");
		viewAllActivities(activityList);
		Helper.line(80, "-");
		String ccaName = Helper.readString("Enter the name of the activity to delete a time slot from: ");
		CCA_Activity selectedActivity = findActivityByName(activityList, ccaName);

		if (selectedActivity != null) {
			setHeader("Delete Time Slot for " + selectedActivity.getName());

			viewAllTimeSlots(activityList);
			Helper.line(80, "-");
			String startTime = Helper.readString("Enter start time of the time slot to delete (HH:mm): ");
			String endTime = Helper.readString("Enter end time of the time slot to delete (HH:mm): ");

			if (validateTimeFormat(startTime) && validateTimeFormat(endTime)) {
				TimeSlot timeSlotToDelete = new TimeSlot(startTime, endTime);

				boolean removed = selectedActivity.getTimeSlots().removeIf(
						existingTimeSlot -> existingTimeSlot.getStartTime().equals(timeSlotToDelete.getStartTime())
								&& existingTimeSlot.getEndTime().equals(timeSlotToDelete.getEndTime()));

				if (removed) {
					System.out.println("Time slot " + startTime + " to " + endTime + " has been deleted for "
							+ selectedActivity.getName() + ".");
				} else {
					System.out.println("Time slot " + startTime + " to " + endTime + " not found for "
							+ selectedActivity.getName() + ".");
				}
			} else {
				System.out.println("Invalid time format. Please use HH:mm format.");
			}
		} else {
			System.out.println("Activity '" + ccaName + "' not found.");
		}
	}

	// ===================== Attendance =====================

	public static void viewAttendanceByCCA(ArrayList<CCA_Activity> activityList) {
		setHeader("View attendance by CCA");

		viewAllActivities(activityList);
		String ccaName = Helper.readString("Enter the name of the CCA to view attendance for: ");
		CCA_Activity selectedActivity = findActivityByName(activityList, ccaName);

		if (selectedActivity != null) {
			System.out.println("Attendance for " + selectedActivity.getName() + ":");
			List<AttendanceEntry> attendanceEntries = selectedActivity.getAttendanceEntries();

			if (attendanceEntries.isEmpty()) {
				System.out.println("No attendance entries available.");
			} else {
				for (AttendanceEntry entry : attendanceEntries) {
					String formattedDate = entry.getAttendanceDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					System.out.println("Student: " + entry.getStudentName() + ", Date: " + formattedDate);
				}
			}
		} else {
			System.out.println("CCA '" + ccaName + "' not found.");
		}
	}

	public static void addAttendance(ArrayList<CCA_Activity> activityList) {
		setHeader("Add Attendance");
		viewAllActivities(activityList);
		Helper.line(80, "-");
		String ccaName = Helper.readString("Enter the name of the activity for which to add attendance: ");
		CCA_Activity selectedActivity = findActivityByName(activityList, ccaName);

		if (selectedActivity != null) {
			setHeader("Add Attendance for " + selectedActivity.getName());

			String studentName = Helper.readString("Enter student's name: ");
			Date attendanceDateInput = Helper.readDate("Enter attendance date (DD/MM/YYYY): ");

			if (studentName.isEmpty() || attendanceDateInput == null) {
				System.out.println("Mandatory fields cannot be empty.");
			} else {
				LocalDate attendanceDate = attendanceDateInput.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				AttendanceEntry newAttendanceEntry = new AttendanceEntry(studentName, attendanceDate);

				if (selectedActivity.addAttendanceEntry(newAttendanceEntry)) {
					String formattedDate = attendanceDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					System.out.println("Attendance entry added for " + studentName + " on " + formattedDate + ".");
				} else {
					System.out.println(
							"Attendance entry for " + studentName + " on " + attendanceDate + " already exists.");
				}
			}
		} else {
			System.out.println("Activity '" + ccaName + "' not found.");
		}
	}

	public static void deleteAttendance(ArrayList<CCA_Activity> activityList) {
		setHeader("Delete Attendance Entry");
		viewAllActivities(activityList);

		String ccaName = Helper.readString("Enter the name of the activity to delete attendance from: ");
		CCA_Activity selectedActivity = findActivityByName(activityList, ccaName);

		if (selectedActivity != null) {
			viewAttendanceByCCAForDeletion(selectedActivity);

			String studentName = Helper
					.readString("Enter the name of the student for the attendance entry to delete: ");
			String attendanceDateStr = Helper
					.readString("Enter the date of the attendance entry to delete (DD/MM/YYYY): ");

			try {
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate attendanceDate = LocalDate.parse(attendanceDateStr, dateFormatter);

				boolean removed = selectedActivity.removeAttendanceEntry(studentName, attendanceDate);

				if (removed) {
					String formattedDate = attendanceDate.format(dateFormatter);
					System.out.println(
							"Attendance entry for " + studentName + " on " + formattedDate + " has been deleted.");
				} else {
					System.out.println("Attendance entry for " + studentName + " on " + attendanceDate + " not found.");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use DD/MM/YYYY format.");
			}
		} else {
			System.out.println("Activity '" + ccaName + "' not found.");
		}
	}

	// ===================== User Accounts =====================

	public static void viewUserAccounts(ArrayList<User> userList) {

		if (userList.isEmpty()) {
			System.out.println("No user accounts found.");
		} else {
			System.out.println("User Accounts:");
			for (User user : userList) {
				System.out.println("Username: " + user.getUsername() + ", Role: " + user.getRole());
			}
		}
	}

	public static void addUserAccount(ArrayList<User> userList) {

		String username = Helper.readString("Enter the new username: ");
		String role = Helper.readString("Enter the role for the user (student/teacher/admin): ");

		// Check if the username already exists
		boolean usernameExists = false;
		for (User existingUser : userList) {
			if (existingUser.getUsername().equalsIgnoreCase(username)) {
				usernameExists = true;
				break;
			}
		}

		if (usernameExists) {
			System.out.println("Username already exists. Please choose a different username.");

		} else {

			if (role.equalsIgnoreCase("student")) {
				userList.add(new Student(username));
			} else if (role.equalsIgnoreCase("teacher")) {
				userList.add(new Teacher(username));
			} else if (role.equalsIgnoreCase("admin")) {
				userList.add(new Administrator(username));
			} else {
				System.out.println("Invalid Role for signup");
			}
		}
		System.out.println("User account added: Username - " + username + ", Role - " + role);
	}

	public static void updateUserAccount(ArrayList<User> userList, String username, String newRole) {
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			if (user.getUsername().equalsIgnoreCase(username)) {
				// Remove the existing user object from the list
				userList.remove(i);

				// Add a new user object of the correct subclass based on the new role
				if (newRole.equalsIgnoreCase("student")) {
					userList.add(i, new Student(username));
				} else if (newRole.equalsIgnoreCase("teacher")) {
					userList.add(i, new Teacher(username));
				} else if (newRole.equalsIgnoreCase("admin")) {
					userList.add(i, new Administrator(username));
				} else {
					System.out.println("Invalid Role for update");
					return; // Exit the method if the role is invalid
				}

				System.out.println("User account updated: Username - " + username + ", New Role - " + newRole);
				return;
			}
		}
		System.out.println("User account not found with username: " + username);
	}

	public static void removeUserAccount(ArrayList<User> userList, String username) {
		User userToRemove = null;

		for (User user : userList) {
			if (user.getUsername().equalsIgnoreCase(username)) {
				userToRemove = user;
				break;
			}
		}

		if (userToRemove != null) {
			userList.remove(userToRemove);
			System.out.println("User account removed with username: " + username);
		} else {
			System.out.println("User account not found with username: " + username);
		}
	}

	// ===================== Registrations =====================

	public static void viewRegisteredActivities(Student student) {
		System.out.println("Registered Activities for " + student.getUsername() + ":");
		List<RegisteredActivity> registeredActivities = student.getRegisteredActivities();

		if (registeredActivities.isEmpty()) {
			System.out.println("No registered activities.");
		} else {
			for (RegisteredActivity activity : registeredActivities) {
				System.out.println("Activity: " + activity.getActivity().getName() + ", Time Slot: "
						+ activity.getTimeSlot().getStartTime() + " - " + activity.getTimeSlot().getEndTime()
						+ ", Approval Status: " + activity.getApprovalStatus());
			}
		}
	}

	public static void registerForActivities(Student student, ArrayList<CCA_Activity> activityList) {
		// Display available activities
		viewAllActivities(activityList);

		String ccaName = Helper.readString("Enter the name of the activity to register for: ");
		CCA_Activity selectedActivity = findActivityByName(activityList, ccaName);

		if (selectedActivity != null) {
			// Display available time slots
			setHeader("Available Time Slots for " + selectedActivity.getName());
			for (TimeSlot timeSlot : selectedActivity.getTimeSlots()) {
				System.out.println("Start Time: " + timeSlot.getStartTime() + ", End Time: " + timeSlot.getEndTime());
			}
			System.out.println();
			String startTime = Helper.readString("Enter start time of the preferred time slot (HH:mm): ");
			String endTime = Helper.readString("Enter end time of the preferred time slot (HH:mm): ");

			if (validateTimeFormat(startTime) && validateTimeFormat(endTime)) {
				TimeSlot preferredTimeSlot = new TimeSlot(startTime, endTime);

				// Check for time slot conflicts
				if (!isTimeSlotAvailable(student, selectedActivity, preferredTimeSlot)) {
					System.out.println("The selected time slot conflicts with your existing schedule.");
				} else if (student.isRegistered(selectedActivity, preferredTimeSlot)) {
					System.out.println("You are already registered for " + selectedActivity.getName() + " from "
							+ startTime + " to " + endTime);
				} else {
					student.registerForActivity(selectedActivity, preferredTimeSlot);
					System.out.println(
							"Registered for " + selectedActivity.getName() + " from " + startTime + " to " + endTime);
				}
			} else {
				System.out.println("Invalid time format. Please use HH:mm format.");
			}
		} else {
			System.out.println("Activity '" + ccaName + "' not found.");
		}
	}

	public static void approveStudentRegistration(Student student, RegisteredActivity registeredActivity) {
		registeredActivity.setApprovalStatus("Approved");
		System.out.println(student.getUsername() + "'s registration for " + registeredActivity.getActivity().getName()
				+ " approved.");
	}

	public static void rejectStudentRegistration(Student student, RegisteredActivity registeredActivity) {
		registeredActivity.setApprovalStatus("Rejected");
		System.out.println(student.getUsername() + "'s registration for " + registeredActivity.getActivity().getName()
				+ " rejected.");
	}

	private static void deleteRegistrations(Student student) {
		List<RegisteredActivity> registrations = student.getRegisteredActivities();
		if (!registrations.isEmpty()) {
			System.out.println("Your registered activities:");
			for (int i = 0; i < registrations.size(); i++) {
				RegisteredActivity registeredActivity = registrations.get(i);
				String approvalStatusBeforeDelete = registeredActivity.getApprovalStatus();
				CCA_Activity activity = registeredActivity.getActivity();
				System.out.println(
						i + ": Activity: " + activity.getName() + " Approval Status: " + approvalStatusBeforeDelete);
			}

			int indexToDelete = Helper.readInt("Enter the index of the registration to delete: ");
			if (indexToDelete >= 0 && indexToDelete < registrations.size()) {
				RegisteredActivity registrationToDelete = registrations.get(indexToDelete);
				String approvalStatus = registrationToDelete.getApprovalStatus();
				if (approvalStatus.equalsIgnoreCase("Pending") || approvalStatus.equalsIgnoreCase("Rejected")) {
					registrations.remove(indexToDelete);
					System.out.println("Registration deleted.");
				} else {
					System.out.println("You can only delete registrations with 'Pending' or 'Rejected' status.");
				}
			} else {
				System.out.println("Invalid index.");
			}
		} else {
			System.out.println("You have no registered activities.");
		}
	}

	private static void viewApprovedAndRejectedApplications(ArrayList<User> userList) {
		if (viewStudentApplicationsNotPending(userList)) {
			int studentIndex = Helper.readInt("Enter the index of the student to view applications: ");
			if (studentIndex >= 0 && studentIndex < userList.size()) {
				User selectedUser = userList.get(studentIndex);
				if (selectedUser instanceof Student) {
					Student student = (Student) selectedUser;
					List<RegisteredActivity> registrations = student.getRegisteredActivities();
					if (!registrations.isEmpty()) {
						System.out.println("Approved and Rejected Applications for " + student.getUsername() + ":");
						for (int i = 0; i < registrations.size(); i++) {
							RegisteredActivity registeredActivity = registrations.get(i);
							String approvalStatus = registeredActivity.getApprovalStatus();
							if (approvalStatus.equalsIgnoreCase("Approved")
									|| approvalStatus.equalsIgnoreCase("Rejected")) {
								CCA_Activity activity = registeredActivity.getActivity();
								System.out.println(i + ":");
								System.out.println("    Activity: " + activity.getName());
								System.out.println("    Approval Status: " + approvalStatus);
							}
						}
					} else {
						System.out.println(student.getUsername() + " has no registered activities.");
					}
				} else {
					System.out.println("Selected user is not a student.");
				}
			} else {
				System.out.println("Invalid student index.");
			}
		} else {
			System.out.println("There are no approved or rejected applications");
		}
	}

	// ====== Extra methods to make the main code a bit easier to read ======
	public static void handleStudentOption(Student student, ArrayList<CCA_Activity> activityList,
			ArrayList<User> userList) {

		int option = 0;

		while (option != 7) {

			displayMenu(student);
			option = Helper.readInt("Enter an option > ");

			if (option == 1) {
				// View available activities
				setHeader("Available Activities");
				viewAllActivities(activityList);

			} else if (option == 2) {
				// View all time slots
				viewAllTimeSlots(activityList);

			} else if (option == 3) {
				// View existing attendance
				setHeader("View attendance by CCA");
				viewAttendanceByCCA(activityList);

			} else if (option == 4) {
				// View registered activities
				setHeader("View registered activities");
				viewRegisteredActivities(student);

			} else if (option == 5) {
				// Register for activities
				setHeader("Register for an activity");
				registerForActivities(student, activityList);

			} else if (option == 6) {
				// Delete registrations
				setHeader("Delete Registrations");
				deleteRegistrations(student);

			} else if (option == 7) {
				System.out.println("\nLogging out...");
				System.out.println("\nSuccessfully Logged out");
				System.out.println("\nThank you for using the School CCA Registration System. Have a nice Day!\n");
			} else {
				System.out.println("Invalid option.");
			}
		}
	}

	public static void handleTeacherOption(Teacher teacher, ArrayList<CCA_Activity> activityList,
			ArrayList<User> userList) {

		int option = 0;

		while (option != 13) {

			displayMenu(teacher);
			option = Helper.readInt("Enter an option > ");

			if (option == 1) {
				// View all CCAs
				setHeader("CCA View List");
				viewAllActivities(activityList);

			} else if (option == 2) {
				// Add a new item
				setHeader("Add an Activity");
				String ccaName = Helper.readString("Enter a new CCA activity name > ");
				addActivity(activityList, ccaName);

			} else if (option == 3) {
				// Delete activity
				deleteActivity(activityList);

			} else if (option == 4) {
				// View all time slots
				viewAllTimeSlots(activityList);

			} else if (option == 5) {
				// Manage time slots
				addTimeSlots(activityList);

			} else if (option == 6) {
				// Delete time slot
				deleteTimeSlot(activityList);

			} else if (option == 7) {
				// View existing attendance
				viewAttendanceByCCA(activityList);

			} else if (option == 8) {
				// Add attendance
				addAttendance(activityList);

			} else if (option == 9) {
				// Delete attendance
				deleteAttendance(activityList);

			} else if (option == 10) {
				// View current student registrations
				setHeader("View current student Registrations");
				boolean hasPendingApplications = viewStudentApplications(userList);

				if (hasPendingApplications) {

				} else {
					System.out.println("No students have pending applications.");
				}

			} else if (option == 11) {
				// Approve or Reject Student Registration
				setHeader("Approve or Reject Student Registration");

				// Display list of students with pending applications
				boolean hasPendingApplications = viewStudentApplicationsForManaging(userList);

				if (hasPendingApplications) {
					Helper.line(80, "-");
					int studentIndex = Helper.readInt("Enter the index of the student to review applications: ");
					Helper.line(80, "-");
					if (studentIndex >= 0 && studentIndex < userList.size()) {
						User selectedUser = userList.get(studentIndex);
						if (selectedUser instanceof Student) {
							Student student = (Student) selectedUser;

							ArrayList<RegisteredActivity> pendingApplications = getPendingApplications(student);

							if (!pendingApplications.isEmpty()) {
								System.out.println("Pending Applications for " + student.getUsername() + ":");
								for (int i = 0; i < pendingApplications.size(); i++) {
									RegisteredActivity registeredActivity = pendingApplications.get(i);
									CCA_Activity activity = registeredActivity.getActivity();
									TimeSlot timeSlot = registeredActivity.getTimeSlot();
									System.out.println(i + ":");
									System.out.println("    Activity: " + activity.getName());
									System.out.println("    Time Slot: " + timeSlot.toString());
									System.out.println("    Approval Status: Pending");
								}

								int applicationIndex = Helper
										.readInt("Enter the index of the application to approve/reject: ");
								if (applicationIndex >= 0 && applicationIndex < pendingApplications.size()) {
									RegisteredActivity selectedApplication = pendingApplications.get(applicationIndex);
									int action = Helper.readInt("Enter 1 to approve or 2 to reject: ");
									if (action == 1) {
										approveStudentRegistration(student, selectedApplication);
									} else if (action == 2) {
										rejectStudentRegistration(student, selectedApplication);
									} else {
										System.out.println("Invalid action.");
									}
								} else {
									System.out.println("Invalid application index.");
								}
							} else {
								System.out.println("No pending applications for " + student.getUsername());
							}
						} else {
							System.out.println("Selected user is not a student.");
						}
					} else {
						System.out.println("Invalid student index.");
					}
				} else {
					System.out.println("No students have pending applications.");
				}

			} else if (option == 12) {
				// View approved and rejected applications
				setHeader("View Approved and Rejected Applications");
				viewApprovedAndRejectedApplications(userList);

			} else if (option == 13) {
				System.out.println("\nLogging out...");
				System.out.println("\nSuccessfully Logged out");
				System.out.println("\nThank you for using the School CCA Registration System. Have a nice Day!\n");
			} else {
				System.out.println("Invalid option.");
			}
		}
	}

	public static void handleAdminOption(Administrator admin, ArrayList<CCA_Activity> activityList,
			ArrayList<User> userList) {

		int option = 0;

		while (option != 14) {

			displayMenu(admin);
			option = Helper.readInt("Enter an option > ");

			if (option == 1) {
				// View all CCAs
				setHeader("CCA View List");
				viewAllActivities(activityList);

			} else if (option == 2) {
				// Add a new item
				setHeader("Add an Activity");
				String ccaName = Helper.readString("Enter a new CCA activity name > ");
				addActivity(activityList, ccaName);

			} else if (option == 3) {
				// Delete activity
				deleteActivity(activityList);

			} else if (option == 4) {
				// View all time slots
				viewAllTimeSlots(activityList);

			} else if (option == 5) {
				// Manage time slots
				addTimeSlots(activityList);

			} else if (option == 6) {
				// Delete time slot
				deleteTimeSlot(activityList);

			} else if (option == 7) {
				// View existing attendance
				viewAttendanceByCCA(activityList);

			} else if (option == 8) {
				// Add attendance
				addAttendance(activityList);

			} else if (option == 9) {
				// Delete attendance
				deleteAttendance(activityList);

			} else if (option == 10) {
				setHeader("View User Accounts");
				viewUserAccounts(userList);

			} else if (option == 11) {
				// Add user account
				addUserAccount(userList);

			} else if (option == 12) {
				// Update user account
				System.out.println();
				viewUserAccounts(userList);
				String username = Helper.readString("Enter the username of the account to update: ");
				String newRole = Helper.readString("Enter the new role for the user (student/teacher/admin): ");
				updateUserAccount(userList, username, newRole);

			} else if (option == 13) {
				// Remove user account
				System.out.println();
				viewUserAccounts(userList);
				String username = Helper.readString("Enter the username of the account to remove: ");
				removeUserAccount(userList, username);

			} else if (option == 14) {
				System.out.println("\nLogging out...");
				System.out.println("\nSuccessfully Logged out");
				System.out.println("\nThank you for using the School CCA Registration System. Have a nice Day!\n");
			} else {
				System.out.println("Invalid option");
			}
		}
	}

	public static int loginOrClose() {
		System.out.println("1. Login");
		System.out.println("2. Sign Up");
		System.out.println("3. Close application");
		return Helper.readInt("Enter your choice: ");
	}

	public static void signup() {
		String username = Helper.readString("Enter your desired username: ");
		String role = Helper.readString("Enter your desired role (student/teacher/admin): ");

		if (role.equalsIgnoreCase("student")) {
			userList.add(new Student(username));

		} else if (role.equalsIgnoreCase("teacher")) {
			userList.add(new Teacher(username));

		} else if (role.equalsIgnoreCase("admin")) {
			userList.add(new Administrator(username));

		} else {
			System.out.println("Invalid Role for signup");
		}

		System.out.println("User account created: Username - " + username + ", Role - " + role);
	}

	public static void displayMenu(User user) {
		if (user instanceof Student) {
			studentMenu(user);
		} else if (user instanceof Teacher) {
			teacherMenu(user);
		} else if (user instanceof Administrator) {
			adminMenu(user);
		}
	}

	public static User login() {
		String username = Helper.readString("Enter your username: ");
		String role = Helper.readString("Enter your role (student/teacher/admin): ");

		for (User user : userList) {
			if (user.getUsername().equalsIgnoreCase(username) && user.getRole().equalsIgnoreCase(role)) {
				return user;
			}
		}

		System.out.println("Invalid username or role.");
		return null;
	}

	public static void studentMenu(User user) {
		setHeader("CCA Activities");
		System.out.println("1. View activities");
		System.out.println("2. View all time slots");
		System.out.println("3. View attendance by CCA");
		System.out.println("4. View registered activities");
		System.out.println("5. Register for an activity");
		System.out.println("6. Delete Registrations");
		System.out.println("7. Log Out");
		Helper.line(80, "-");
	}

	public static void teacherMenu(User user) {
		setHeader("CCA Activities");
		System.out.println("1. View activities");
		System.out.println("2. Add an activity");
		System.out.println("3. Delete an activity");
		System.out.println("4. View all time slots");
		System.out.println("5. Add time Slots");
		System.out.println("6. Delete Time Slot");
		System.out.println("7. View attendance by CCA");
		System.out.println("8. Add attendance");
		System.out.println("9. Delete attendance");
		System.out.println("10. View current student registrations");
		System.out.println("11. Approve or Reject Student Registration");
		System.out.println("12. View Approved and Rejected Applications");
		System.out.println("13. Log Out");
		Helper.line(80, "-");
	}

	public static void adminMenu(User user) {
		setHeader("CCA Activities");
		System.out.println("1. View activities");
		System.out.println("2. Add an activity");
		System.out.println("3. Delete an activity");
		System.out.println("4. View all time slots");
		System.out.println("5. Add time Slots");
		System.out.println("6. Delete Time Slot");
		System.out.println("7. View attendance by CCA");
		System.out.println("8. Add attendance");
		System.out.println("9. Delete attendance");
		System.out.println("10. View user accounts");
		System.out.println("11. Add user account");
		System.out.println("12. Update user account");
		System.out.println("13. Remove user account");
		System.out.println("14. Log Out");
		Helper.line(80, "-");
	}

	public static void setHeader(String header) {
		Helper.line(80, "-");
		System.out.println(header);
		Helper.line(80, "-");
	}

	public static String retrieveAllActivities(ArrayList<CCA_Activity> activityList) {
		String output = "";

		for (int i = 0; i < activityList.size(); i++) {
			output += activityList.get(i).getName() + "\n";
		}

		if (output.isEmpty()) {
			output = "There are no CCA Activities!";
		}
		return output;
	}

	private static boolean validateTimeFormat(String time) {
		String pattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
		return time.matches(pattern);
	}

	private static CCA_Activity findActivityByName(ArrayList<CCA_Activity> activityList, String name) {
		for (CCA_Activity activityName : activityList) {
			if (activityName.getName().equalsIgnoreCase(name)) {
				return activityName;
			}
		}
		return null;
	}

	public static boolean performDeleteActivity(ArrayList<CCA_Activity> activityList, String name) {
		if (name.isEmpty())
			return false;

		for (int i = 0; i < activityList.size(); i++) {
			if (name.equalsIgnoreCase(activityList.get(i).getName())) {
				activityList.remove(i);
				return true;
			}
		}
		return false;
	}

	private static boolean isTimeConflict(TimeSlot existingTimeSlot, TimeSlot newTimeSlot) {
		LocalTime existingStartTime = LocalTime.parse(existingTimeSlot.getStartTime());
		LocalTime existingEndTime = LocalTime.parse(existingTimeSlot.getEndTime());
		LocalTime newStartTime = LocalTime.parse(newTimeSlot.getStartTime());
		LocalTime newEndTime = LocalTime.parse(newTimeSlot.getEndTime());

		return (newStartTime.isAfter(existingStartTime) && newStartTime.isBefore(existingEndTime))
				|| (newEndTime.isAfter(existingStartTime) && newEndTime.isBefore(existingEndTime))
				|| (newStartTime.isBefore(existingStartTime) && newEndTime.isAfter(existingEndTime));
	}

	public static void viewAttendanceByCCAForDeletion(CCA_Activity selectedActivity) {
		System.out.println("Attendance for " + selectedActivity.getName() + ":");

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		List<AttendanceEntry> attendanceEntries = selectedActivity.getAttendanceEntries();

		if (attendanceEntries.isEmpty()) {
			System.out.println("No attendance entries available.");
		} else {
			for (AttendanceEntry entry : attendanceEntries) {
				String formattedDate = entry.getAttendanceDate().format(dateFormatter);
				System.out.println("Student: " + entry.getStudentName() + ", Date: " + formattedDate);
			}
		}
	}

	private static boolean isTimeSlotAvailable(User user, CCA_Activity activity, TimeSlot timeSlot) {
		if (user instanceof Student) {
			Student student = (Student) user;
			List<RegisteredActivity> registeredActivities = student.getRegisteredActivities();

			for (RegisteredActivity registeredActivity : registeredActivities) {
				if (registeredActivity.getTimeSlot().conflictsWith(timeSlot)) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean viewStudentApplications(ArrayList<User> userList) {
		boolean hasPendingApplications = false;
		System.out.println("List of Students with Pending Applications:");
		for (User user : userList) {
			if (user instanceof Student) {
				Student student = (Student) user;
				if (!student.getRegisteredActivities().isEmpty()) {
					for (RegisteredActivity registeredActivity : student.getRegisteredActivities()) {
						String approvalStatus = registeredActivity.getApprovalStatus();
						if (approvalStatus.equalsIgnoreCase("Pending")) {
							hasPendingApplications = true;
							CCA_Activity activity = registeredActivity.getActivity();
							TimeSlot timeSlot = registeredActivity.getTimeSlot();
							System.out.println(userList.indexOf(user) + ": " + student.getUsername());
							System.out.println("    Activity: " + activity.getName());
							System.out.println("    Time Slot: " + timeSlot.toString());
							System.out.println("    Approval Status: " + approvalStatus);
						}
					}
				}
			}
		}
		return hasPendingApplications;
	}

	private static boolean viewStudentApplicationsForManaging(ArrayList<User> userList) {
		boolean hasPendingApplications = false;
		System.out.println("List of Students with Pending Applications:");
		for (User user : userList) {
			if (user instanceof Student) {
				Student student = (Student) user;
				if (!student.getRegisteredActivities().isEmpty()) {
					for (RegisteredActivity registeredActivity : student.getRegisteredActivities()) {
						String approvalStatus = registeredActivity.getApprovalStatus();
						if (approvalStatus.equalsIgnoreCase("Pending")) {
							hasPendingApplications = true;
							System.out.println(userList.indexOf(user) + ": " + student.getUsername());
						}
					}
				}
			}
		}
		return hasPendingApplications;
	}

	private static boolean viewStudentApplicationsNotPending(ArrayList<User> userList) {
		boolean hasNotPendingApplications = false;
		System.out.println("List of Students with Approved or Rejected Applications:");
		for (User user : userList) {
			if (user instanceof Student) {
				Student student = (Student) user;
				if (!student.getRegisteredActivities().isEmpty()) {
					for (RegisteredActivity registeredActivity : student.getRegisteredActivities()) {
						String approvalStatus = registeredActivity.getApprovalStatus();
						if ((approvalStatus.equalsIgnoreCase("Approved"))
								|| (approvalStatus.equalsIgnoreCase("Rejected"))) {
							hasNotPendingApplications = true;
							System.out.println(userList.indexOf(user) + ": " + student.getUsername());
						}
					}
				}
			}
		}
		return hasNotPendingApplications;
	}

	private static ArrayList<RegisteredActivity> getPendingApplications(Student student) {
		ArrayList<RegisteredActivity> pendingApplications = new ArrayList<>();
		for (RegisteredActivity registeredActivity : student.getRegisteredActivities()) {
			String approvalStatus = registeredActivity.getApprovalStatus();
			if (approvalStatus.equalsIgnoreCase("Pending")) {
				pendingApplications.add(registeredActivity);
			}
		}
		return pendingApplications;
	}

	private static Student getStudentFromActivity(CCA_Activity activity) {
		for (User user : userList) {
			if (user instanceof Student) {
				Student student = (Student) user;
				for (RegisteredActivity registeredActivity : student.getRegisteredActivities()) {
					if (registeredActivity.getActivity() == activity) {
						return student;
					}
				}
			}
		}
		return null;
	}
}