package GA;

import java.time.LocalTime;

public class TimeSlot {
	private String startTime;
	private String endTime;

	public TimeSlot(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean conflictsWith(TimeSlot otherTimeSlot) {
		LocalTime thisStartTime = LocalTime.parse(startTime);
		LocalTime thisEndTime = LocalTime.parse(endTime);
		LocalTime otherStartTime = LocalTime.parse(otherTimeSlot.getStartTime());
		LocalTime otherEndTime = LocalTime.parse(otherTimeSlot.getEndTime());

		// Check for conflicts
		return (thisStartTime.isBefore(otherEndTime) && thisEndTime.isAfter(otherStartTime))
				|| (otherStartTime.isBefore(thisEndTime) && otherEndTime.isAfter(thisStartTime))
				|| thisStartTime.equals(otherStartTime) || thisEndTime.equals(otherEndTime);
	}

	@Override
	public String toString() {
		return startTime + " - " + endTime;
	}
}
