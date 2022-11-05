package event;

import java.util.HashMap;

import javax.naming.NameAlreadyBoundException;

import newChannel.Broker;

// Gère les Broker et leurs noms

public class BrokerManager {
	private static volatile BrokerManager instance = null;
	private HashMap<String, Broker> map = new HashMap<>();

	private BrokerManager() {
	}

	public final static BrokerManager getInstance() {
		if (BrokerManager.instance == null) {
			synchronized (BrokerManager.class) {
				if (BrokerManager.instance == null) {
					BrokerManager.instance = new BrokerManager();
				}
			}
		}
		return BrokerManager.instance;
	}

	// Enregistre un Broker dans la hashMap
	synchronized public void EnregistrerBroker(Broker brok) throws NameAlreadyBoundException {
		if (map.putIfAbsent(brok.m_name, brok) != null) {
			throw new NameAlreadyBoundException();
		}
		notifyAll();
	}

	synchronized public Broker getMyBroker(String name) {
		while (map.get(name) == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map.get(name);
	}
	
	void SupprBroker (String name) {
		map.remove(name);
	}
	
	void SupprimerMap( ) {
		map.clear();
	}

}
