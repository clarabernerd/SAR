package event;



public abstract class MessageQueue {
	
	public abstract interface IListener {
		
		void received(byte[] msg);
		void sent (byte[] bytes, int offset, int length, Object cookie);
		void closed();
	}
	
	public abstract void setListener(IListener listener);
	
	public abstract boolean send (byte[] bytes, Object cookie);
	
	abstract boolean send(byte[] bytes, int offset, int length, Object cookie);
	
	abstract void close();
	
	abstract boolean closed();

		
}
