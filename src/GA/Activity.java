package GA;

import java.util.ArrayList;
import java.util.List;

public class Activity {
	private String id;
	private String name;
	private String category;
	private int capacity;
	private String date;
	private String time;
	private ArrayList<TimeSlot> timeSlots;

	public ArrayList<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	public Activity(String id, String name) {
		this.id = id;
		this.name = name;
		this.timeSlots = new ArrayList<>();
	}

	public boolean containsTimeSlot(TimeSlot timeSlot) {
		for (TimeSlot existingSlot : timeSlots) {
			if (existingSlot.equals(timeSlot)) {
				return true;
			}
		}
		return false;
	}

	public void addTimeSlot(String startTime, String endTime) {
		TimeSlot timeSlot = new TimeSlot(startTime, endTime);
		if (!timeSlots.contains(timeSlot)) {
			timeSlots.add(timeSlot);
		}
	}

	public Activity(String id, String name, String category, int capacity, String date, String time) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.capacity = capacity;
		this.date = date;
		this.time = time;
	}

	public void updateActivity(String id, String name, String category, int capacity, String date, String time) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.capacity = capacity;
		this.date = date;
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getDate() {
		return (String) date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return (String) time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
