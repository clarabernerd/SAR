package exemple;

public class Task2 extends Task {
	Broker my_broker;
	Channel my_channel;
	
	public Task2(Broker broker) {
		my_broker = broker;
	}
	public void run() {
		my_channel = my_broker.accept(1234);
		byte[] bytes = new byte[1024];
		my_channel.read(bytes, 0, bytes.length);
		my_channel.disconnect();
	}

}
