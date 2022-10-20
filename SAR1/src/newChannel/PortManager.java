package newChannel;

import java.net.PortUnreachableException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

//Créer pour chaque broker, permet de gérer tout les ports d'un broker
public class PortManager {
	ImplBroker brok;

	// Liste de tout les port du broker qui ont une connexion
	private HashMap<Integer, ImplChannel> my_ports = new HashMap<>();

	// Liste de tout les port du broker qui sont en attente de connexion
	private LinkedList<Entry<Integer, ImplBroker>> connectBroker = new LinkedList<>();

	protected PortManager(ImplBroker broker) {
		brok = broker;
	}

	protected synchronized ImplChannel accept(int port) throws PortUnreachableException, InterruptedException {
		if (my_ports.get(port) != null) {
			throw new PortUnreachableException("Le port est déjà utilisé");
		}
		while (connectBroker.stream().noneMatch(entry -> entry.getKey() == port))
			wait();
		Entry<Integer, ImplBroker> matchingEntry = connectBroker.stream().filter(entry -> entry.getKey() == port)
				.findFirst().get();
		ImplBroker brokerEnDemande = matchingEntry.getValue();
		CircularBuffer bufferA = new CircularBuffer(100);
		CircularBuffer bufferB = new CircularBuffer(100);
		ImplChannel channelCourante = new ImplChannel(brok, port, bufferA, bufferB);
		ImplChannel deuxièmeChannel = new ImplChannel(brokerEnDemande, bufferB, bufferA);
		channelCourante.setSecondChannel(deuxièmeChannel);
		deuxièmeChannel.setSecondChannel(channelCourante);
		AssocierPort(port, channelCourante);
		connectBroker.remove(matchingEntry);
		notifyAll();
		return channelCourante;

	}

	// permet à un broker de demander une connexion sur le même port
	// Le broker est bloquer tant que la connexion n'a pas été acceptée par le
	// portmanager
	protected synchronized ImplChannel DemandeCo(int port, ImplBroker BrokerAttente) {
		connectBroker.add(new SimpleEntry<>(port, BrokerAttente));
		notifyAll();
		while (my_ports.get(port) == null
				|| !my_ports.get(port).getMyChannel().brok.m_name.equals(BrokerAttente.m_name)) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return my_ports.get(port).getMyChannel();
	}

	// Associer Channel et port
	protected synchronized void AssocierPort(int port, ImplChannel channel) throws PortUnreachableException {
		if (my_ports.get(port) != null) {
			throw new PortUnreachableException("Le port est déjà utilisé");
		}
		my_ports.put(port, channel);
	}

	protected synchronized void LibérerPort(int port) {
		my_ports.remove(port);
	}

}
