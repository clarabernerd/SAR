package event;

import java.util.LinkedList;
import java.util.List;

public class Executor extends Thread {
	
	List<Event> events;
	
	public Executor() {
		this.events = new LinkedList<Event>();
	}
	
	public void run () {
		while(true) {
			if (events.isEmpty())
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			Event e = events.remove(0);
			e.reac();
					
		}
	}
	
	public void post (Event e) {
		events.add(e);
	}


}
