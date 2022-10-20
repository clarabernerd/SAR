package newChannel;

import java.io.IOException;

public class Task extends Thread {

	public String m_name;
	private Broker m_broker;
	private Runnable m_runnable;

	Task(String name) {
		m_name = name;
		m_broker = new ImplBroker(name);

	}

	public void run() {
		m_runnable.run();
	}

	public Broker getBroker() {
		return m_broker;
	}

	public void setRunnable(Runnable runnable) {
		m_runnable = runnable;
	}

}
