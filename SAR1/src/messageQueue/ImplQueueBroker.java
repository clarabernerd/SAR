package messageQueue;

import java.net.PortUnreachableException;

public class ImplQueueBroker extends QueueBroker {
	
	ImplBroker brok;
	String my_name;

	ImplQueueBroker(String name) {
		super(name);
		my_name = name;
		brok = new ImplBroker(name);
	}

	@Override
	MessageQueue accept(int port) throws PortUnreachableException, InterruptedException {
		Channel chann = brok.accept(port);
		ImplMessageQueue queue = new ImplMessageQueue((ImplChannel) chann);
		return queue;
	}

	@Override
	MessageQueue connect(String name, int port) throws PortUnreachableException {
		Channel chann = brok.connect(name, port);
		ImplMessageQueue queue = new ImplMessageQueue((ImplChannel) chann);
		return queue;
	}

}
