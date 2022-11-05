package event;

import event.QueueBroker.IConnectListener;
import newChannel.Channel;
import newChannel.ImplChannel;
import newChannel.ImplBroker;

public class EventConnect extends Event{
	String name;
	int port; 
	ImplBroker brok;
	IConnectListener listener;

	EventConnect(Executor e, String name, int port, ImplBroker brok, IConnectListener listener) {
		super(e);
		this.name = name; 
		this.port = port;
		this.brok = brok;
		this.listener = listener;
		// TODO Auto-generated constructor stub
	}

	@Override
	void reac() {
	    newChannel.Channel chann = brok.connect(name, port);
		ImplMessageQueue queue = new ImplMessageQueue(e, (newChannel.ImplChannel) chann);
		listener.connected(name, port, queue);
	}

}
