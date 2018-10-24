package EmployeeManagementSystem;


/**
* EmployeeManagementSystem/LocationHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/g-harel/Documents/dev/DEMS/DistributedEmployeeManagementSystem/src/EmployeeManagementSystem.idl
* Wednesday, October 24, 2018 5:08:40 PM EDT
*/

abstract public class LocationHelper
{
  private static String  _id = "IDL:EmployeeManagementSystem/Location:1.0";

  public static void insert (org.omg.CORBA.Any a, EmployeeManagementSystem.Location that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static EmployeeManagementSystem.Location extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (EmployeeManagementSystem.LocationHelper.id (), "Location");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static EmployeeManagementSystem.Location read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_LocationStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, EmployeeManagementSystem.Location value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static EmployeeManagementSystem.Location narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof EmployeeManagementSystem.Location)
      return (EmployeeManagementSystem.Location)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      EmployeeManagementSystem._LocationStub stub = new EmployeeManagementSystem._LocationStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static EmployeeManagementSystem.Location unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof EmployeeManagementSystem.Location)
      return (EmployeeManagementSystem.Location)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      EmployeeManagementSystem._LocationStub stub = new EmployeeManagementSystem._LocationStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
