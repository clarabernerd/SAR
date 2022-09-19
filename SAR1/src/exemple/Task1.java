package exemple;

public class Task1 extends Task {
	
	Broker my_broker;
	Channel my_channel;
	
	public Task1 (Broker broker) {
		my_broker = broker;
	}
	public void run() {
		my_channel = my_broker.connect("Test 1", 1234);
		byte[] bytes = new byte[1024];
		my_channel.write(bytes, 0, bytes.length);
		my_channel.disconnect();
	}
}
