package newChannel;

import java.net.PortUnreachableException;

import javax.naming.NameAlreadyBoundException;

public class ImplBroker extends Broker {

	public BrokerManager brokerMap = BrokerManager.getInstance();
	PortManager portManager = new PortManager(this);

	public ImplBroker(String name) {
		super(name);
		try {
			brokerMap.EnregistrerBroker(this);
		} catch (NameAlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	public Channel accept(int port) throws PortUnreachableException, InterruptedException {
		return portManager.accept(port);
	}

	public Channel connect(String name, int port) {
		ImplBroker i = (ImplBroker) brokerMap.getMyBroker(name);
		return i.portManager.DemandeCo(port, this);
	}
	
	public void SupprBroker() {
		brokerMap.SupprBroker(m_name);
	}

}
