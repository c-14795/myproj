public class parent {
	private int pid;
	private String name;

	public parent()

	{
		super();
	}

	public parent(String name, int pid) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.pid = pid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
