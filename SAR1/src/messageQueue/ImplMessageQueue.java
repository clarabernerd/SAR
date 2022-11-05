package messageQueue;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ImplMessageQueue extends MessageQueue{
	
	static final int length_header = 12;
	private boolean closed = false;
	private ImplChannel channel;
	
	public ImplMessageQueue(ImplChannel chann){
		channel = chann;
	}
	

	@Override
	void send(byte[] bytes, int offset, int length) throws IOException, InterruptedException {
		byte[] lengthBte = ByteBuffer.allocate(length_header).putInt(length).array();
		int bteSent = 0;
		while (bteSent < length_header) { //tant qu'on pas envoyé toute la taille du message dans le header
			bteSent += channel.write(lengthBte, bteSent, length - bteSent);
		}
		bteSent = 0;
		while(bteSent < length) { //tant qu'on a pas envoyé tout le message
			bteSent += channel.write(bytes, offset + bteSent, length - bteSent);
		}
	}

	@Override
	byte[] receive() throws IOException, InterruptedException {
		byte[] bufferH = new byte[12];
		byte[] message;
		int bteRead = 0;
		
		while (bteRead < length_header) { //on commence par lire la taille du message
			bteRead += channel.read(bufferH, bteRead, length_header);
		}
		
		int longueur = ByteBuffer.wrap(bufferH).getInt(); //voir cours année dernière
		bteRead = 0;
		message = new byte[longueur];
		
		while(bteRead < longueur) {
			bteRead += channel.read(message, bteRead, longueur - bteRead);
		}
		return message;
	}

	@Override
	void close() {
		channel.disconnect();
		closed = true;
		
	}

	@Override
	boolean closed() {
		// TODO Auto-generated method stub
		return closed;
	}

}
