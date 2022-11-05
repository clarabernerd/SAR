package event;

import newChannel.ImplBroker;

public class ImplQueueBroker extends QueueBroker {
	
	Executor exe;
	ImplBroker brok;

	public ImplQueueBroker(String name, Executor exe) {
		super(name);
		this.exe = exe;
		brok = new ImplBroker(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public
	boolean bind(int port, IAcceptListener listener) {
		exe.post(new EventBind(exe, port, brok, listener));
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	boolean unbind(int port) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean connect(String name, int port, IConnectListener listener) {
		exe.post(new EventConnect(exe, name, port, brok, listener));
		return true;
	}

}
