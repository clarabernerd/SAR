package event;

import event.Executor;

public abstract class Event {
	Executor e;
	
	public Event(Executor e) {
		this.e =e;
	}
	
	public void reac() {
		
	}
}
