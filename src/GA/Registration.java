package GA;

public class Registration {
	private CCA_Activity activity;
	private TimeSlot timeSlot;

	public Registration(CCA_Activity activity, TimeSlot timeSlot) {
		this.activity = activity;
		this.timeSlot = timeSlot;
	}

	public CCA_Activity getActivity() {
		return activity;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}
}
