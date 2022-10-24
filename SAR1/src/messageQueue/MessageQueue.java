package messageQueue;

import java.io.IOException;

abstract class MessageQueue {

	abstract void send(byte[] bytes, int offset, int lenght) throws IOException, InterruptedException;

	abstract byte[] receive() throws IOException, InterruptedException;

	abstract void close();

	abstract boolean closed();

}
