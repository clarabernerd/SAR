package messageQueue;

import java.net.PortUnreachableException;

abstract class QueueBroker {

	QueueBroker(String name) {
	}

	abstract MessageQueue accept(int port) throws PortUnreachableException, InterruptedException;

	abstract MessageQueue connect(String name, int port) throws PortUnreachableException;

}
