package exo_2809;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Broker {

	private String name;
	private List<Integer> waiters;
	private HashMap<Integer, Channel> open;

	public Broker(String name) {
		this.name = name;
		this.waiters = new ArrayList<Integer>();
		this.open = new HashMap<Integer, Channel>();
		Main.BrokerDealer.put(name, this);
	}

	synchronized Channel accept(int port) throws InterruptedException {
		if (this.open.containsKey(port) || this.waiters.contains(port)) {
			throw new InterruptedException("Le port" + port + "est déjà utilisé");
		}

		this.waiters.add(port);

		while (waiters.contains(port)) {
			wait();
		}

		Channel locale = this.open.get(port);
		synchronized(locale) {
		locale.notifyAll();
		}

		System.out.println("Le Broker " + this.name + "est bien connecté sur le port " + port);

		return locale;
	}

	synchronized Channel connect(String name, int port) throws InterruptedException {

		while (!Main.BrokerDealer.containsKey(name)) {
			Thread.sleep(200);
		}

		Broker BrokerConnexion = Main.BrokerDealer.get(name);
		Channel locale = new Channel();
		Channel externe = new Channel();
		locale.connectTo(externe);
		externe.connectTo(locale);

		if (BrokerConnexion.waitingStatus(port)) {
			BrokerConnexion.openConnect(port, externe);
		}
		synchronized(BrokerConnexion) {
		BrokerConnexion.notifyAll();
		}

		while (BrokerConnexion.waiters.contains(port)) {
			synchronized(externe) {
			externe.wait();
			}
		}

		this.open.put(port, locale);
		System.out.println("Le Broker " + this.name + " est bien connecté sur le port " + port);
		return locale;

	}

	public boolean waitingStatus(int port) {
		return this.waiters.contains(port);
	}

	public void openConnect(int port, Channel externe) {
		this.open.put(port, externe);
		this.waiters.remove(this.waiters.indexOf(port));
	}
}
