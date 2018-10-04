package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class CenterServer extends UnicastRemoteObject implements RMIInterface {
	public HashMap<Character, ArrayList<Record>> records;
	public String location;
	
	public CenterServer(String location) throws RemoteException {
		super(0);
		
		this.location = location;
	}
	
	public String getName() {
		return "//localhost/" + this.location;
	}
	
	public void bind() throws MalformedURLException, RemoteException {
    	Naming.rebind(this.getName(), this);
	}
	
	public String getRecordCounts() {
		return "test count";
	}

	public static void main(String[] args) {
	    try {
	    	LocateRegistry.createRegistry(1099); 
	    	new CenterServer("CA").bind();
		    new CenterServer("US").bind();
		    new CenterServer("UK").bind();
	    } catch (Exception e) {
	    	System.out.println(e);
	    }
	}

}
