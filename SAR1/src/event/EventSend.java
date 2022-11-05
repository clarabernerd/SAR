package event;

import java.io.IOException;
import java.nio.ByteBuffer;

import event.MessageQueue.IListener;
import newChannel.Channel;
import newChannel.ImplChannel;

public class EventSend extends Event{
	
	IListener listener;
	ImplChannel chann; 
	Object cookie;
	int length_header = 12;
	byte[] bytes;
	int offset;
	int length;

	EventSend(Executor e, IListener listener, ImplChannel chann,Object cookie, byte[] bytes, int offset, int length) {
		super(e);
		this.chann = chann;
		this.listener = listener;
		this.cookie = cookie;
		this.bytes = bytes;
		this.offset = offset;
		this.length = length;
		
		// TODO Auto-generated constructor stub
	}


	@Override
	void reac() {
		
		ImplChannel channVoisine = chann.getMyChannel();
		try {
			IListener listenerVoisin = MapListener.getInstance().getMyListner(channVoisine);
			e.post(new EventReceive(e, channVoisine, listenerVoisin, length_header));
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		byte[] lengthBte = ByteBuffer.allocate(length_header).putInt(length).array();
		int bteSent = 0;
		while (bteSent < length_header) { //tant qu'on pas envoyé toute la taille du message dans le header
			try {
				bteSent += chann.write(lengthBte, bteSent, length - bteSent);
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bteSent = 0;
		while(bteSent < length) { //tant qu'on a pas envoyé tout le message
			try {
				bteSent += chann.write(bytes, offset + bteSent, length - bteSent);
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		listener.sent(lengthBte, offset, length, cookie);
		
		// TODO Auto-generated method stub
		
	}

}
