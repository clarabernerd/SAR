package event;

public abstract class MessageQueue {
	IListener l;
	
	public interface IListener {
		
		void received(byte[] msg);
		void send (byte[] bytes, int offset, int length, Object cookie);
		void closed();
	}
	
	void setListener(IListener l) {
		this.l = l;
	}
	
	public boolean send (byte[] bytes, Object cookie) {
		return false;
	}
	
	public boolean send(byte[] bytes, int offset, int length, Object cookie) {
		return false;
	}
	
	public void close() {
	}
	
	public boolean closed() {
		return false;
	}
	
}
