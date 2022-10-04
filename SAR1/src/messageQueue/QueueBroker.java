package messageQueue;

import java.util.HashMap;

public class QueueBroker {
		
	private Broker broker;
	private HashMap<Integer, MessageQueue> messagequeue;
	
	
	QueueBroker(String name) {
		this.broker = new Broker(name);
		this.messagequeue = new HashMap<Integer, MessageQueue>();
	}

	MessageQueue accept(int port) {
		try {
			Channel channel = this.broker.accept(port);
			MessageQueue mqueue = new MessageQueue(channel);
			messagequeue.put(port, mqueue);
			return mqueue;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	MessageQueue connect(String name, int port) {
		try {
			Channel channel = this.broker.connect(name, port);
			MessageQueue mqueue = new MessageQueue(channel);
			messagequeue.put(port,mqueue);
			return mqueue;
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
