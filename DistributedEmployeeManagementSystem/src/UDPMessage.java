class UDPMessage {
	public String type;
	public String body;
	
	public UDPMessage() {
		this.type = "";
		this.body = "";
	}
	
	public UDPMessage(String raw) {
		String[] parts = raw.split("\r\n");
		this.type = parts[0];
		if (parts.length > 1) {
			this.body = parts[1];
		} else {
			this.body = "";
		}
	}
	
	public byte[] toBuffer() {
		byte[] buffer = String.format("%s\r\n%s", this.type, this.body).getBytes();
		if (buffer.length > 576) {
			Logger.err("UDP message is too large (type: \"%s\", body: \"%s\"", this.type, this.body);
		}
		return buffer;
	}
}
