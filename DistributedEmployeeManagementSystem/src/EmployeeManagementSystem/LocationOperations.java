package EmployeeManagementSystem;


/**
* EmployeeManagementSystem/LocationOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/g-harel/Documents/dev/DEMS/DistributedEmployeeManagementSystem/src/EmployeeManagementSystem.idl
* Wednesday, October 24, 2018 11:27:03 PM EDT
*/

public interface LocationOperations 
{
  String createMRecord (String managerID, String firstName, String lastName, int employeeID, String mailID, String project, String location);
  String createERecord (String managerID, String firstName, String lastName, int employeeID, String mailID, String projectID);
  String getRecordCounts (String managerID);
  String editRecord (String managerID, String recordID, String fieldName, String newValue);
  String transferRecord (String managerID, String recordID, String remoteCenterServerName);
  void shutdown ();
} // interface LocationOperations
