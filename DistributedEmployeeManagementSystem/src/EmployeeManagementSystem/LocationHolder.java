package EmployeeManagementSystem;

/**
* EmployeeManagementSystem/LocationHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/g-harel/Documents/dev/DEMS/DistributedEmployeeManagementSystem/src/EmployeeManagementSystem.idl
* Wednesday, October 24, 2018 11:27:03 PM EDT
*/

public final class LocationHolder implements org.omg.CORBA.portable.Streamable
{
  public EmployeeManagementSystem.Location value = null;

  public LocationHolder ()
  {
  }

  public LocationHolder (EmployeeManagementSystem.Location initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = EmployeeManagementSystem.LocationHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    EmployeeManagementSystem.LocationHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return EmployeeManagementSystem.LocationHelper.type ();
  }

}
