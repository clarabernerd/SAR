package messageQueue;

import java.io.IOException;

import javax.naming.NameAlreadyBoundException;

public class Main {

	public static void main(String[] args) throws NameAlreadyBoundException, InterruptedException {
		Task taskA = new Task("taskA");
		taskA.setRunnable(() -> {
			try {
				QueueBroker brokA = taskA.getMyQueueBroker();
				for (int i=0; i<5;i++) {
				MessageQueue messageQueue = brokA.accept(1234);
				byte[] buff = new byte[100];
				byte[] r_bytes = messageQueue.receive();
				messageQueue.send(buff, 0, 12);
				messageQueue.close();
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
					QueueBroker brokB = taskB.getMyQueueBroker();
					System.out.println("la task "+count+" veut se connecter");
					MessageQueue messageQueue = brokB.connect("taskA", 1234);
					System.out.println("La task "+count+" à établit une connexion");
					String message = "message de " + count;
					messageQueue.send(message.getBytes(),0,12);
					byte[] buff = new byte[message.length()];
					messageQueue.receive();
					System.out.println(message);
					assert new String(buff).equals(message);
					messageQueue.close();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					assert false : e;
				}
				
			});
			
			taskB.start();
		}
		
		taskA.join();
		
		
	}
	

}
