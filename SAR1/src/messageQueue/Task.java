package messageQueue;

import java.io.IOException;
import java.net.PortUnreachableException;

import javax.naming.NameAlreadyBoundException;

import newChannel.Broker;
import newChannel.ImplBroker;

public class Task extends Thread {

	public String m_name;
	private  QueueBroker queueBroker;
	private Runnable m_runnable;

	Task(String name) {
		m_name = name;
		queueBroker = new ImplQueueBroker(name);

	}

	public void run() {
		m_runnable.run();
	}

	public QueueBroker getMyQueueBroker() {
		return queueBroker;
	}

	public void setRunnable(Runnable runnable) {
		m_runnable = runnable;
	}

}
