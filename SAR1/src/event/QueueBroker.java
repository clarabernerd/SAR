package event;

public abstract class QueueBroker {
	
	QueueBroker(String name) {
	}
	
	public interface IAcceptListener {
		void accepted (int port, MessageQueue queue);
	}
	
	public abstract boolean bind(int port, IAcceptListener listener);
	
	abstract boolean unbind(int port);
	
	public interface IConnectListener{
		void connected (String name, int port, MessageQueue queue);
		void refused(String name, int port);
	}
	
	abstract boolean connect (String name, int port, IConnectListener listener);

}
