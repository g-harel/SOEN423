package common;

import java.io.IOException;
import java.rmi.Remote;

public interface CenterServerInterface extends Remote {
	public Boolean createMRecord(String firstName, String lastName, int employeeID, String mailID, Project project) throws IOException;
	public Boolean createERecord(String firstName, String lastName, int employeeID, String mailID, String projectID, String location) throws IOException;
	public String getSelfRecordCounts() throws IOException;
	public String getRecordCounts() throws IOException;
	public String editRecord(String recordID, String fieldName, String newValue) throws IOException;
}
