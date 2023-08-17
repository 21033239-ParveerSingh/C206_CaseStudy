package GA;

public class Registration {
	private Student student;
	private Activity activity;
	private boolean approval;

	public Registration(Student student, Activity activity) {
		this.student = student;
		this.activity = activity;
	}

	public Registration(Student student, Activity activity, boolean approval) {
		this.student = student;
		this.activity = activity;
		this.approval = approval;
	}

	public Student getStudent() {
		return student;
	}

	public Activity getActivity() {
		return activity;
	}

	public void addApproval(Registration registration, boolean approval) {
		new Registration(registration.getStudent(), registration.getActivity(), approval);
	}
}
