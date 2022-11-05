package event;

import java.io.IOException;
import java.nio.ByteBuffer;

import event.MessageQueue.IListener;
import newChannel.ImplChannel;

public class EventReceive extends Event{
	ImplChannel chann;
	IListener listener;
	int length_header;

	EventReceive(Executor e, ImplChannel chann, IListener listener, int length_header) {
		super(e);
		this.chann = chann;
		this.listener = listener;
		this.length_header = length_header;
		// TODO Auto-generated constructor stub
	}

	@Override
	void reac() {
		
		byte[] bufferH = new byte[12];
		byte[] message;
		int bteRead = 0;
		
		while (bteRead < length_header) { //on commence par lire la taille du message
			try {
				bteRead += chann.read(bufferH, bteRead, length_header);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int longueur = ByteBuffer.wrap(bufferH).getInt(); //voir cours année dernière
		bteRead = 0;
		message = new byte[longueur];
		
		while(bteRead < longueur) {
			try {
				bteRead += chann.read(message, bteRead, longueur - bteRead);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		listener.received(message);
		
		
	}

}
