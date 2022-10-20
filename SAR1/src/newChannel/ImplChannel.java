package newChannel;

import java.io.IOException;

public class ImplChannel extends Channel {
	private final int my_port;
	protected ImplBroker brok;
	private ImplChannel second_channel;
	final CircularBuffer write_buffer;
	final CircularBuffer read_buffer;

	ImplChannel(ImplBroker broker, CircularBuffer rBuffer, CircularBuffer wBuffer) {
		brok = broker;
		my_port = -1;
		write_buffer = wBuffer;
		read_buffer = rBuffer;

		
	}

	ImplChannel(ImplBroker broker, int port, CircularBuffer rBuffer, CircularBuffer wBuffer) {
		brok = broker;
		my_port = port;
		write_buffer = wBuffer;
		read_buffer = rBuffer;

	}

	public int read(byte[] bytes, int offset, int length) throws IOException {
		synchronized (read_buffer) {
			while (read_buffer.empty()) {
				try {
					read_buffer.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int i;
			for (i = 0; i < length; i++) {
				if (disconnected()) {
					throw new IOException("Le channel a été déconnecté");
				}
				try {
				bytes[offset + i] = read_buffer.pull();
				}catch (IllegalStateException e) {
				break;
				}
			}
			read_buffer.notify();
			return i;
		}
	}

	public int write(byte[] bytes, int offset, int length) throws InterruptedException,  IOException {
		synchronized (write_buffer) {
			while (write_buffer.full())
				try {
					write_buffer.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			int i;
			for (i = 0; i < length; i++) {
				if (second_channel.disconnected()) {
					throw new IOException("Le channel à été déconnecté");
				}
				try {
				write_buffer.push(bytes[i + offset]);
				}catch(IllegalStateException e) {
				break;
				}
				
			}
			write_buffer.notify();
			return i;
		}

	}

	public void disconnect() {
		super.disconnect();
		if (my_port != -1) {
			brok.portManager.LibérerPort(my_port);
		}
	}

	public boolean disconnected() {
		return !m_connected;
	}

	void setSecondChannel(ImplChannel chann) {
		second_channel = chann;
	}

	ImplChannel getMyChannel() {
		return second_channel;
	}

}
