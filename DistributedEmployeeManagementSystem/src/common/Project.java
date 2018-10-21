package common;

public class Project {
	public String projectID;
	public String clientName;
	public String projectName;

	public Project() {}

	public Project(String serialized) {
		String data[] = serialized.split(";", 3);
		if (data.length > 0) {
			this.projectID = data[0].trim();
		}
		if (data.length > 1) {
			this.clientName = data[1].trim();
		}
		if (data.length > 2) {
			this.projectName = data[2].trim();
		}
	}

	public String toString() {
		return String.format("%s;%s;%s", projectID, clientName, projectName);
	}
}
