package DEMS;


/**
* DEMS/LocationPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/g-harel/Documents/dev/DEMS/DistributedEmployeeManagementSystem/src/DEMS.idl
* Saturday, October 20, 2018 2:46:42 o'clock PM EDT
*/

public abstract class LocationPOA extends org.omg.PortableServer.Servant
 implements DEMS.LocationOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_address", new java.lang.Integer (0));
    _methods.put ("createMRecord", new java.lang.Integer (1));
    _methods.put ("createERecord", new java.lang.Integer (2));
    _methods.put ("getRecordCounts", new java.lang.Integer (3));
    _methods.put ("editRecord", new java.lang.Integer (4));
    _methods.put ("transferRecord", new java.lang.Integer (5));
    _methods.put ("shutdown", new java.lang.Integer (6));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // DEMS/Location/_get_address
       {
         String $result = null;
         $result = this.address ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // DEMS/Location/createMRecord
       {
         String managerID = in.read_string ();
         String firstName = in.read_string ();
         String lastName = in.read_string ();
         int employeeID = in.read_long ();
         String mailID = in.read_string ();
         String project = in.read_string ();
         String location = in.read_string ();
         boolean $result = false;
         $result = this.createMRecord (managerID, firstName, lastName, employeeID, mailID, project, location);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // DEMS/Location/createERecord
       {
         String managerID = in.read_string ();
         String firstName = in.read_string ();
         String lastName = in.read_string ();
         int employeeID = in.read_long ();
         String mailID = in.read_string ();
         String projectID = in.read_string ();
         boolean $result = false;
         $result = this.createERecord (managerID, firstName, lastName, employeeID, mailID, projectID);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 3:  // DEMS/Location/getRecordCounts
       {
         String managerID = in.read_string ();
         String $result = null;
         $result = this.getRecordCounts (managerID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // DEMS/Location/editRecord
       {
         String managerID = in.read_string ();
         String recordID = in.read_string ();
         String fieldName = in.read_string ();
         String newValue = in.read_string ();
         String $result = null;
         $result = this.editRecord (managerID, recordID, fieldName, newValue);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // DEMS/Location/transferRecord
       {
         String managerID = in.read_string ();
         String recordID = in.read_string ();
         String remoteCenterServerName = in.read_string ();
         boolean $result = false;
         $result = this.transferRecord (managerID, recordID, remoteCenterServerName);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 6:  // DEMS/Location/shutdown
       {
         this.shutdown ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:DEMS/Location:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Location _this() 
  {
    return LocationHelper.narrow(
    super._this_object());
  }

  public Location _this(org.omg.CORBA.ORB orb) 
  {
    return LocationHelper.narrow(
    super._this_object(orb));
  }


} // class LocationPOA
