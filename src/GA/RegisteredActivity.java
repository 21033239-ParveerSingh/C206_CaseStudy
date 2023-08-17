package GA;

public class RegisteredActivity {
	private CCA_Activity activity;
	private TimeSlot timeSlot;
	private String approvalStatus;

	public RegisteredActivity(CCA_Activity activity, TimeSlot timeSlot) {
		this.activity = activity;
		this.timeSlot = timeSlot;
	}

	public CCA_Activity getActivity() {
		return activity;
	}

	public void setActivity(CCA_Activity activity) {
		this.activity = activity;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
}
