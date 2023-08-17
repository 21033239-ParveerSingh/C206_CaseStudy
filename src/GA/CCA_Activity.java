/*
 * I declare that this code was written by me. 
 * I do not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: 22037444
 * Student ID: {type your id here}
 * Class: {type your class here}
 * Date/Time created: Saturday 12-08-2023 18:52
 */

package GA;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 22037444
 *
 */
public class CCA_Activity {
	public static void main(String[] args) {

		// Preset Activity (Remove if not needed after asking faci)
		ArrayList<CCA_Activity_Name> activityList = new ArrayList<CCA_Activity_Name>();
		activityList.add(new CCA_Activity_Name("Volleyball"));

		int option = 0;

		// Welcome message
		String welcome = "";
		Helper.line(80, "-");
		welcome += "Welcome to the School CCA Registration System!";
		System.out.println(welcome);

		while (option != 10) {

			menu();
			option = Helper.readInt("Enter an option > ");

			if (option == 1) {
				// View all CCAs
				setHeader("CCA View List");
				viewAllActivities(activityList);

			} else if (option == 2) {
				// Add a new item
				addActivity(activityList);

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
				System.out.println("\nThank you for using the School CCA Registration System. Have a nice Day!");
			} else {
				System.out.println("Invalid option");
			}
		}
	}

	// Option 1 View activities (CRUD- Read)
	public static void viewAllActivities(ArrayList<CCA_Activity_Name> activityList) {
		String output = String.format("CCA NAMES:\n\n");
		output += retrieveAllActivities(activityList);
		System.out.println(output);
	}

	// Option 2 Add new activity (CRUD - Create)
	public static void addActivity(ArrayList<CCA_Activity_Name> activityList) {
		setHeader("Add an Activity");

		String ccaName = Helper.readString("Enter a new CCA activity name > ");

		for (CCA_Activity_Name activityName : activityList) {
			if (ccaName.equalsIgnoreCase(activityName.getName())) {
				System.out.println("That activity already exists!");
				return;
			}
		}

		CCA_Activity_Name newActivity = new CCA_Activity_Name(ccaName);
		activityList.add(newActivity);
		System.out.println("Activity '" + ccaName + "' has been added.");
	}

	// Option 3 Delete an Activity (CRUD -
	public static void deleteActivity(ArrayList<CCA_Activity_Name> activityList) {
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

	public static void viewAllTimeSlots(ArrayList<CCA_Activity_Name> activityList) {
		setHeader("View All Time Slots");
		for (CCA_Activity_Name activityName : activityList) {
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

	public static void addTimeSlots(ArrayList<CCA_Activity_Name> activityList) {
		setHeader("Manage Time Slots");
		viewAllActivities(activityList);
		Helper.line(80, "-");
		String ccaName = Helper.readString("Enter the name of the activity to add a time slot for: ");
		CCA_Activity_Name selectedActivity = findActivityByName(activityList, ccaName);

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

	public static void deleteTimeSlot(ArrayList<CCA_Activity_Name> activityList) {
		setHeader("Delete Time Slot");
		viewAllActivities(activityList);
		Helper.line(80, "-");
		String ccaName = Helper.readString("Enter the name of the activity to delete a time slot from: ");
		CCA_Activity_Name selectedActivity = findActivityByName(activityList, ccaName);

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

	public static void viewAttendanceByCCA(ArrayList<CCA_Activity_Name> activityList) {
		setHeader("View attendance by CCA");

		viewAllActivities(activityList);
		String ccaName = Helper.readString("Enter the name of the CCA to view attendance for: ");
		CCA_Activity_Name selectedActivity = findActivityByName(activityList, ccaName);

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

	public static void addAttendance(ArrayList<CCA_Activity_Name> activityList) {
		setHeader("Add Attendance");
		viewAllActivities(activityList);
		Helper.line(80, "-");
		String ccaName = Helper.readString("Enter the name of the activity for which to add attendance: ");
		CCA_Activity_Name selectedActivity = findActivityByName(activityList, ccaName);

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

	public static void deleteAttendance(ArrayList<CCA_Activity_Name> activityList) {
		setHeader("Delete Attendance Entry");
		viewAllActivities(activityList);

		String ccaName = Helper.readString("Enter the name of the activity to delete attendance from: ");
		CCA_Activity_Name selectedActivity = findActivityByName(activityList, ccaName);

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

	// Extra methods to make the main code a bit easier to read
	public static void menu() {
		setHeader("CCA Activities");
		System.out.println("1. View activites");
		System.out.println("2. Add an activity");
		System.out.println("3. Delete an activity");
		System.out.println("4. View all time slots");
		System.out.println("5. Add time Slots");
		System.out.println("6. Delete Time Slot");
		System.out.println("7. View attendance by CCA");
		System.out.println("8. Add attendance");
		System.out.println("9. Delete attendance");
		System.out.println("10. Quit");
		Helper.line(80, "-");
	}

	public static void setHeader(String header) {
		Helper.line(80, "-");
		System.out.println(header);
		Helper.line(80, "-");
	}

	public static String retrieveAllActivities(ArrayList<CCA_Activity_Name> activityList) {
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

	private static CCA_Activity_Name findActivityByName(ArrayList<CCA_Activity_Name> activityList, String name) {
		for (CCA_Activity_Name activityName : activityList) {
			if (activityName.getName().equalsIgnoreCase(name)) {
				return activityName;
			}
		}
		return null;
	}

	public static boolean performDeleteActivity(ArrayList<CCA_Activity_Name> activityList, String name) {
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

	public static void viewAttendanceByCCAForDeletion(CCA_Activity_Name selectedActivity) {
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

}
