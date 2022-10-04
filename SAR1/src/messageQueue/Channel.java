package messageQueue;

import java.io.IOException;

public class Channel {

	private boolean disconnected;
	private Channel connectedTo;
	private CircularBuffer buff;

	public Channel() {
		this.disconnected = false;
		this.buff = new CircularBuffer(5);
	}

	public int read(byte[] bytes, int offset, int length) throws IOException {
		if (this.connectedTo.disconnected() || this.disconnected) {
			this.connectedTo.disconnect();
			this.disconnect();
			throw new IOException("Les Channel ont été déconnetés");
		}
		int count = 0;
		while (count < bytes.length - 1 && count < length - 1) {
			try {
				while (this.buff.empty()) {
					synchronized(this) {
					this.wait();
					}
				}
				byte bte = buff.pull();
				bytes[offset + count] = bte;
				count++;
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Impossible de reccupérer le byte");
				return 0;
			}
			synchronized (this.connectedTo){
			this.connectedTo.notifyAll();
			}
		}
		return count;
	}

	public int write(byte[] bytes, int offset, int length) throws IOException {
		if (this.connectedTo.disconnected() || this.disconnected) {
			this.connectedTo.disconnect();
			this.disconnect();
			throw new IOException("Les Channel ont été déconnetés");
		}
		int count = 0;

		while (count < length && count < bytes.length) {
			try {
				while (this.connectedTo.buff.full()) {
					synchronized(this) {
					this.wait();
					}
				}
				if (this.connectedTo.disconnected() || this.disconnected) {
					this.connectedTo.disconnect();
					this.disconnect();
					throw new IOException("Les Channel ont été déconnetés");
				}
				this.connectedTo.buff.push(bytes[offset + count]);
				count++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Can't put bytes");
				return 0;
			}
			synchronized(this.connectedTo) {
			this.connectedTo.notifyAll();
			}
		}
		return count;
	}

	public void disconnect() {
		this.disconnected = true;
	}

	public boolean disconnected() {
		return this.disconnected;
	}

	public void connectTo(Channel externe) {
		this.connectedTo = externe;
	}
}
