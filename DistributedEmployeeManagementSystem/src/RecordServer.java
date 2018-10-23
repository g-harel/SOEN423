import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class Message {
	public String type;
	public String body;
	
	public Message() {
		this.type = "";
		this.body = "";
	}
	
	public Message(String raw) {
		String[] parts = raw.split("\r\n");
		this.type = parts[0];
		if (parts.length > 1) {
			this.body = parts[1];
		} else {
			this.body = "";
		}
	}
	
	public String toString() {
		return String.format("%s\r\n%s", this.type, this.body);
	}
}

public class RecordServer extends RecordStore {
	private AddressBook ab;
	
	public RecordServer(AddressBook ab) {
		super();
		this.ab = ab;
	}
	
	public synchronized void listen() throws IOException {
		DatagramSocket socket = new DatagramSocket(this.ab.selfPort());
		byte[] buffer = new byte[256];
		
		while (true) {
			DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
			socket.receive(requestPacket);
			Message request = new Message(new String(requestPacket.getData(), 0, requestPacket.getLength()));
			
			Message response = null;
			switch(request.type) {
			case "count":
				response = handleCount();
				break;
			}
			
			if (response != null) {
				response.type = "response";
				DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length, requestPacket.getAddress(), requestPacket.getPort());
				socket.send(responsePacket);
			}
		}
	}
	
	public synchronized void send(String locationCode, Message msg) throws IOException {
		DatagramSocket socket = new DatagramSocket(this.ab.names().length + 1 + this.ab.selfPort());
		byte[] buffer = msg.toString().getBytes();
		
		int port = this.ab.port(locationCode);
		DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), port);
	}
	
	///
	
	public Message handleCount() {
		Logger.log("[UDP] received count request");
		Message res = new Message();
		res.body = ab.selfName() + " " + this.count();
		return res;
	}
}
