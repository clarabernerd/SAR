package newChannel;

import java.io.IOException;

abstract public class Channel {
	boolean m_connected = true;

	int read(byte[] bytes, int offset, int length) throws IOException {
		return 0;
	}

	int write(byte[] bytes, int offset, int length) throws IOException, InterruptedException {
		return 0;
	}

	public void disconnect() {
		m_connected = false;
	}

	public boolean disconnected() {
		return !m_connected;
	}

}
