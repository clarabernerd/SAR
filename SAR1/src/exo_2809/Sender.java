package exo_2809;

import java.io.IOException;

public class Sender extends Task {

	Broker brok;

	public Sender(String name) {
		this.brok = new Broker(name);
	}

	public void run() {
		Channel chann;
		try {
			chann = brok.accept(1234);
			byte[] message = ("mess").getBytes();
			chann.write(message, 0, message.length);

		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
