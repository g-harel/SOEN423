public abstract class Record {
	private static int count;
	public static String prefix = "";

	public Record() {
		this.recordID = this.prefix + String.format("%05d", this.count++);
	}

	public String recordID;
	public String firstName;
	public String lastName;
	public int employeeID;
	public String mailID;
}
