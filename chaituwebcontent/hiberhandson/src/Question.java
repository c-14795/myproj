import java.util.List;

public class Question {

	private int id;
	private String qname;
	private List<Answer> option;
	private String qanswer;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQname() {
		return qname;
	}
	public void setQname(String qname) {
		this.qname = qname;
	}
	public List<Answer> getOption() {
		return option;
	}
	public void setOption(List<Answer> option) {
		this.option = option;
	}
	public String getQanswer() {
		return qanswer;
	}
	public void setQanswer(String qanswer) {
		this.qanswer = qanswer;
	}
	
}
