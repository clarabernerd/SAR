package messageQueue;

abstract class MessageQueue {
	
	void send(byte[] bytes, int offset, int length) {

	}

	byte[] receive() {
		return null;
	}

	void close() {

	}

	boolean closed() {
		return false;

	}

}
