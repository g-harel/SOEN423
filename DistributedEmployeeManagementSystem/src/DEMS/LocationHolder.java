package DEMS;

/**
* DEMS/LocationHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/g-harel/Documents/dev/DEMS/DistributedEmployeeManagementSystem/src/DEMS.idl
* Monday, October 22, 2018 12:53:01 PM EDT
*/

public final class LocationHolder implements org.omg.CORBA.portable.Streamable
{
  public DEMS.Location value = null;

  public LocationHolder ()
  {
  }

  public LocationHolder (DEMS.Location initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DEMS.LocationHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DEMS.LocationHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DEMS.LocationHelper.type ();
  }

}
