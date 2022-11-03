package event;

public abstract class QueueBroker {
	String name; 
	
	public QueueBroker(String name) {
		this.name = name;
	}
	
	public interface IAcceptListener {
		void accepted (int port, MessageQueue queue);
	}
	
	public boolean bind(int port, IAcceptListener listener) {
		return false;
	}
	
	public boolean unbind(int port) {
		return false;
	}
	
	public interface ConnectListener{
		void connected (String name, int port, MessageQueue queue);
		void refused(String name, int port);
	}
	
	public boolean connect (String name, int port, ConnectListener listener) {
		return false;
	}

}
