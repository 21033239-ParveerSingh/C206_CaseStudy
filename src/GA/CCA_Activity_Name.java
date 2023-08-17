/*
 * I declare that this code was written by me. 
 * I do not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: 22037444
 * Student ID: {type your id here}
 * Class: {type your class here}
 * Date/Time created: Saturday 12-08-2023 18:43
 */

package GA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 22037444
 *
 */

public class CCA_Activity_Name {
	private String name;
	private List<TimeSlot> timeSlots;
	private List<AttendanceEntry> attendanceEntries;

	public CCA_Activity_Name(String name) {
		this.name = name;
		this.timeSlots = new ArrayList<>();
		this.attendanceEntries = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addTimeSlot(TimeSlot timeSlot) {
		timeSlots.add(timeSlot);
	}

	public List<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	public boolean addAttendanceEntry(AttendanceEntry attendanceEntry) {
		if (!attendanceEntries.contains(attendanceEntry)) {
			attendanceEntries.add(attendanceEntry);
			return true;
		}
		return false;
	}

	public List<AttendanceEntry> getAttendanceEntries() {
		return attendanceEntries;
	}

	public boolean removeAttendanceEntry(String studentName, LocalDate attendanceDate) {
		AttendanceEntry entryToRemove = null;
		for (AttendanceEntry entry : attendanceEntries) {
			if (entry.getStudentName().equalsIgnoreCase(studentName)
					&& entry.getAttendanceDate().equals(attendanceDate)) {
				entryToRemove = entry;
				break;
			}
		}

		if (entryToRemove != null) {
			attendanceEntries.remove(entryToRemove);
			return true;
		}
		return false;
	}
}
