package newChannel;

import java.io.IOException;
import java.net.PortUnreachableException;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Task taskA = new Task("taskA");
		taskA.setRunnable(() -> {
			try {
				Broker brokA = taskA.getBroker();
				for (int i=0; i <5; i++) {
				Channel chann = brokA.accept(1234);
				System.out.println("Connexion acceptée pour "  + ((ImplChannel)chann).getMyChannel().brok.m_name);
				byte[] buff = new byte[100];
				int r_bytes = chann.read(buff, 0, 20 + String.valueOf(i).length());
				chann.write(buff, 0, r_bytes);
				chann.disconnect();
				}
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		taskA.start();
		
		for (int i=0; i<5; i++) {
			Task taskB = new Task("taskB" + i);
			int count = i;
			taskB.setRunnable(() -> {
				
				try {
					Broker brokB = taskB.getBroker();
					System.out.println("la task "+count+" veut se connecter");
					Channel chann = brokB.connect("taskA", 1234);
					System.out.println("La task "+count+" à établit une connexion");
					String message = "message de " + count;
					chann.write(message.getBytes(), 0, message.length());
					byte[] buff = new byte[message.length()];
					chann.read(buff, 0, message.length());
					System.out.println(message);
					assert new String(buff).equals(message);
					chann.disconnect();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					assert false : e;
				}
				
			});
			
			taskB.start();
		}
		
		taskA.join();
		BrokerManager.getInstance().SupprimerMap();
	}
}