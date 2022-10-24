package messageQueue;

import javax.naming.NameAlreadyBoundException;

public class Task extends Thread {

	public String my_name;
	private QueueBroker queueBrok;
	private Runnable runna;

	Task(String name) throws NameAlreadyBoundException {
		my_name = name;
		queueBrok = new ImplQueueBroker(name);
	}

	public void run() {
		runna.run();
	}

	public void setRunnable(Runnable runnable) {
		runna = runnable;
	}

	public QueueBroker getMyQueueBroker() {
		return queueBrok;
	}


}
