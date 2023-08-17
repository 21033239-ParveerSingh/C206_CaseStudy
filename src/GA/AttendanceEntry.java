package GA;

import java.time.LocalDate;

public class AttendanceEntry {
	private String studentName;
	private LocalDate attendanceDate;

	public AttendanceEntry(String studentName, LocalDate attendanceDate) {
		this.studentName = studentName;
		this.attendanceDate = attendanceDate;
	}

	public String getStudentName() {
		return studentName;
	}

	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		AttendanceEntry that = (AttendanceEntry) obj;
		return studentName.equals(that.studentName) && attendanceDate.equals(that.attendanceDate);
	}

	@Override
	public int hashCode() {
		return studentName.hashCode() + attendanceDate.hashCode();
	}
}
