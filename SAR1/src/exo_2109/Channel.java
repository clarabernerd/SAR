package exo_2109;

public class Channel {
	public int read(byte[] bytes, int offset, int length) { return 0; }
    public int write(byte[] bytes, int offset, int length) { return 0; }
    public void disconnect() {}
    public boolean disconnected() { return false; }
}
