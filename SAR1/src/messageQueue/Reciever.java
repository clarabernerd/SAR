package messageQueue;

import java.io.IOException;

public class Reciever extends Task {

	Broker brok;
	String connect;

	public Reciever(String name, String connect) {
		this.connect = connect;
		this.brok = new Broker(name);
	}

	public void run() {
		Channel chann;
		try {
			chann = brok.connect(connect, 1234);
			byte[] bte = new byte[1024];
			int length = chann.read(bte, 0, 5);
			System.out.print("Message reçus : ");
			for (int i = 0; i < length; i++) {
				System.out.println((char) bte[i]);
			}
			System.out.println();
			chann.disconnect();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
