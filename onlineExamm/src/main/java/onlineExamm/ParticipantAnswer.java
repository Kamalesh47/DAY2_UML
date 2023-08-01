package onlineExamm;

public class ParticipantAnswer {
	private int id;
    private int participantId;
    private int questionId;
    private String selectedOption;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParticipantId() {
		return participantId;
	}
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getSelectedOption() {
		return selectedOption;
	}
	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}
	public ParticipantAnswer(int id, int participantId, int questionId, String selectedOption) {
		super();
		this.id = id;
		this.participantId = participantId;
		this.questionId = questionId;
		this.selectedOption = selectedOption;
	}
	public ParticipantAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

}
