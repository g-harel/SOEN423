import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class RecordServer extends RecordStore implements Runnable {
	private AddressBook ab;
	private int recordIDCount = 0;
	private boolean running = true;
	private DatagramSocket serverSocket;
	private DatagramSocket clientSocket;

	public RecordServer(AddressBook ab) {
		super();
		this.ab = ab;

		int serverPort = this.ab.selfPort();
		try {
			this.serverSocket = new DatagramSocket(serverPort);
		} catch (SocketException e) {
			Logger.err("could not bind to server socket \"%s\": %s", serverPort, e);
		}
		Logger.log("bound to server port \"%s\"", serverPort);

		int clientPort = this.ab.total() + this.ab.selfPort();
		try {
			this.clientSocket = new DatagramSocket(clientPort);
		} catch (SocketException e) {
			Logger.err("could not bind to client socket \"%s\": %s", clientPort, e);
		}
		Logger.log("bound to client port \"%s\"", clientPort);
	}
	
	public void add(Record record) {
		int ID = this.ab.selfIndex() + (this.ab.total() * this.recordIDCount++);
		record.recordID = record.getType() + String.format("%05d", ID);
		this.write(record);
	}

	public void kill() {
		this.running = false;
		this.serverSocket.close();
		this.clientSocket.close();
		Thread.currentThread().interrupt();
	}

	public void run() {
		byte[] buffer;

		while (this.running) {
			buffer = new byte[256];
			DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
			try {
				this.serverSocket.receive(requestPacket);
			} catch (IOException e) {
				Logger.err("could not receive request: %s", e);
				continue;
			}
			UDPMessage request = new UDPMessage(new String(requestPacket.getData(), 0, requestPacket.getLength()));

			Logger.log("[UDP] <<< \"%s\"", request.type);

			UDPMessage response = null;
			switch(request.type) {
			case RecordServer.typeList:
				response = this.handleList();
				break;
			case RecordServer.typeCount:
				response = this.handleCount();
				break;
			}

			if (response == null) {
				Logger.err("no configured handler for request type \"%s\"", request.type);
				continue;
			}

			response.type = "response";
			buffer = response.toBuffer();
			DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length, requestPacket.getAddress(), requestPacket.getPort());
			try {
				this.serverSocket.send(responsePacket);
			} catch (IOException e) {
				Logger.err("could not send response: %s", e);
				continue;
			}

			Logger.log("[UDP] >>> \"%s\"", request.type);
		}
	}

	public UDPMessage send(String locationCode, UDPMessage msg) {
		DatagramSocket socket;
		byte[] buffer;

		int port = this.ab.port(locationCode);
		InetAddress addr;

		try {
			addr= InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			Logger.err("%s", e);
			return null;
		}

		buffer = msg.toBuffer();
		DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length, addr, port);
		try {
			this.clientSocket.send(requestPacket);
		} catch (IOException e) {
			Logger.err("could not send request: %s", e);
			return null;
		}

		Logger.log("[UDP] >>> \"%s\" \"%s\"", locationCode, msg.type);

		buffer = new byte[256];
		DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
		try {
			// TODO timeout
			this.clientSocket.receive(responsePacket);
		} catch (IOException e) {
			Logger.err("could not receive request: %s", e);
			return null;
		}

		UDPMessage response = new UDPMessage(new String(responsePacket.getData(), 0, responsePacket.getLength()));

		Logger.log("[UDP] <<< \"%s\" \"%s\"", locationCode, msg.type);

		return response;
	}

	///

	private static final String typeList = "list";

	public UDPMessage handleList() {
		UDPMessage res = new UDPMessage();
		res.body = ab.selfName() + " " + this.count();
		return res;
	}

	public String sendList(String locationCode) throws IOException {
		UDPMessage response = this.send(locationCode, new UDPMessage(RecordServer.typeList));
		return response.body;
	}

	public String sendListAll() {
		String res = this.handleList().body;
		for (String val : this.ab.names()) {
			try {
				res += ", " + sendList(val);
			} catch (IOException e) {}
		}
		return res;
	}

	///
	
	public static final String typeCount = "count";

	public UDPMessage handleCount() {
		UDPMessage res = new UDPMessage();
		res.body = String.format("%d", this.recordIDCount);
		return res;
	}

	///

	// TODO transfer record
}
