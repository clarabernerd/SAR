package newChannel;

import java.net.PortUnreachableException;

abstract public class Broker {
	String m_name;

	Broker(String name) {
		m_name = name;
	}

	public Channel accept(int port) throws PortUnreachableException, InterruptedException {
		return null;
	}

	public Channel connect(String name, int port) {
		return null;
	}

}
