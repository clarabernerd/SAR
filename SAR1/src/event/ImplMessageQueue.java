package event;

import event.MessageQueue.IListener;
import newChannel.Channel;
import newChannel.ImplChannel;

public class ImplMessageQueue extends MessageQueue{
	
	Executor exe;
	ImplChannel chann;
	IListener listener;
	
	public ImplMessageQueue (Executor exe, ImplChannel chann ) {
		this.chann = chann;
		this.exe = exe;
	}

	@Override
	public
	void setListener(IListener listener) {
		this.listener = listener;
		MapListener.getInstance().add(chann, listener);
	}

	@Override
	public
	boolean send(byte[] bytes, Object cookie) {
		return send(bytes, 0, bytes.length, cookie);
	}

	@Override
	void close() {
		exe.post(new EventClose(exe,listener,chann));
		}

	@Override
	boolean closed() {
		return chann.disconnected();
	}

	@Override
	boolean send(byte[] bytes, int offset, int length, Object cookie) {
		if (closed() == true) {
			return false;
		} else {
			exe.post(new EventSend(exe, listener, chann, cookie, bytes, offset, length));
		}
		return true;
	}
}
