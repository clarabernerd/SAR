package messageQueue;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MessageQueue {
	
	private Channel channel;
	private boolean close;
	
	MessageQueue (Channel channel){
		this.close = false;
		this.channel = channel;
		
	}
	
	void send(byte[] bytes, int offset, int length) {
		byte[] messageLength = ByteBuffer.allocate(Integer.BYTES).putInt(length).array();
		int count = 0;
		try {
			count = this.channel.write(messageLength, 0, Integer.BYTES);
			count += this.channel.write(bytes, 0, length);
			if(count != length+Integer.BYTES) {
				throw new IOException("Le nombre de byte n'est pas bon");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	byte[] receive() {
		byte[] bytes = new byte[64];
		
		try {
			int res = this.channel.read(bytes, 0, Integer.BYTES);
			if(res != Integer.BYTES) {
				throw new IOException("Ne peut pas lire la longueur");
			}
			int length = (bytes[0]<<24)&0xff000000 |
					(bytes[1]<<16)&0x00ff0000 |
					(bytes[2]<<8)&0x0000ff00 |
					(bytes[3]<<0)&0x000000ff;
			res = this.channel.read(bytes, 4, length);
			return bytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	void close() {
		this.close = true;
	}

	boolean closed() {
		return this.closed();

	}

}
