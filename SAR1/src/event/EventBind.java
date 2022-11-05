package event;

import java.net.PortUnreachableException;

import event.QueueBroker.IAcceptListener;
import newChannel.Channel;
import newChannel.ImplChannel;
import newChannel.ImplBroker;

public class EventBind extends Event{
	
	int port; 
	ImplBroker brok;
	IAcceptListener listener;


	EventBind(Executor e, int port, ImplBroker brok, IAcceptListener listener) {
		super(e);
		this.port = port;
		this.brok = brok;
		this.listener = listener;
		// TODO Auto-generated constructor stub
	}

	@Override
	void reac() {
		
		Channel chann;
		try {
			chann = brok.accept(port);
			ImplMessageQueue queue = new ImplMessageQueue(e,(ImplChannel) chann);
			listener.accepted(port, queue);
		} catch (PortUnreachableException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	

}
