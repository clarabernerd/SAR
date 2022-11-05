package event;

import java.util.HashMap;

import event.MessageQueue.IListener;
import newChannel.Channel;
import newChannel.ImplChannel;

public class MapListener {
	
	public static MapListener mapL;
	
	public HashMap<Channel, IListener> map;
	
	public MapListener() {
		map = new HashMap<>();
	}
	
	public synchronized static MapListener getInstance() {
		if(mapL == null) {
			mapL = new MapListener();
		}
		return mapL;
	}
	
	public synchronized void add(ImplChannel chann, IListener lis) {
		map.put(chann, lis);
		notifyAll();
	}
	
	public synchronized IListener getMyListner(ImplChannel chann) throws InterruptedException {
		IListener lis = map.get(chann);
		while(lis == null) {
			wait();
			lis = map.get(chann);
		}
		return lis;
	}
	
	public synchronized IListener Suppr(ImplChannel chann) {
		return map.remove(chann);
	}
	
	public synchronized void SupprMap() {
		map.clear();
	}
	
	
	
	

}
